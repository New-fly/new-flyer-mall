package org.wlgzs.xf_mall.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.*;
import org.wlgzs.xf_mall.entity.*;
import org.wlgzs.xf_mall.entity.Collection;
import org.wlgzs.xf_mall.service.ProductService;
import org.wlgzs.xf_mall.util.IdsUtil;
import org.wlgzs.xf_mall.util.PageUtil;
import org.wlgzs.xf_mall.util.PageUtilTwo;
import org.wlgzs.xf_mall.util.ReadFiles;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Auther: 阿杰
 * @Date: 2018/4/18 21:21
 * @Description:
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductCategoryRepository productCategoryRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private CollectionRepository collectionRepository;
    @Autowired
    FootprintRepository footprintRepository;
    @Autowired
    private OrdersRepository ordersRepository;

    //分页遍历商品  搜索商品
    @Override
    public Page<Product> getProductListPage(String product_keywords, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<Product> specification = new PageUtil<Product>(product_keywords).getPage("product_keywords", "product_serviceType", "product_category");
        Page<Product> pages = productRepository.findAll(specification, pageable);
        return pages;
    }

    //添加商品
    @Override
    public void saveProduct(String product_details, MultipartFile[] myFileNames, HttpSession session, HttpServletRequest request) {
        String realName = "";
        String[] str = new String[myFileNames.length];
        for (int i = 0; i < myFileNames.length; i++) {
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                String fileName = myFileNames[i].getOriginalFilename();
                String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());

                // 生成实际存储的真实文件名
                realName = UUID.randomUUID().toString() + fileNameExtension;

                // "/upload"是你自己定义的上传目录
                String realPath = session.getServletContext().getRealPath("/upload");
                File uploadFile = new File(realPath, realName);
                try {
                    myFileNames[i].transferTo(uploadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                str[i] = request.getContextPath() + "/upload/" + realName;
            }
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < str.length; i++) {
            if (!myFileNames[i].getOriginalFilename().equals("")) {
                stringBuffer.append(str[i] + ",");
            }
        }
        String product_picture = stringBuffer.substring(0, stringBuffer.length() - 1);

        Map<String, String[]> properties = request.getParameterMap();
        Product product = new Product();
        try {
            BeanUtils.populate(product, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        product.setProduct_details(product_details);
        product.setProduct_picture(product_picture);
        productRepository.save(product);
    }

    //富文本添加图片
    @Override
    public String[] uploadImg(MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        String realName = "";
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());

            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;

            // "/upload"是你自己定义的上传目录
            String realPath = session.getServletContext().getRealPath("/upload");
            File uploadFile = new File(realPath, realName);
            try {
                myFileName.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String[] str = {request.getContextPath() + "/upload/" + realName};
        return str;
    }

    //删除商品
    @Override
    public String delete(long productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            String path = request.getSession().getServletContext().getRealPath("/");
            String image = product.getProduct_picture();

            String[] img = image.split(",");
            for (int i = 0; i < img.length; i++) {
                File file = new File(path + "" + img[i].substring(1, img[0].length()));
                if (file.exists() && file.isFile()) {
                    file.delete();
                }
            }
            productRepository.deleteById(productId);
            return "成功";
        } else {
            return "失败";
        }

    }

    //通过id查找商品
    @Override
    public Product findProductById(long productId) {
        return productRepository.findById(productId);
    }

    //通过id查询商品  返回集合
    @Override
    public List<Product> findProductListById(long productId) {
        List<Product> products = productRepository.findByIdReturnOne(productId);
        String img;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProduct_picture().contains(",")) {
                img = products.get(i).getProduct_picture();
                img = img.substring(0, img.indexOf(","));
                System.out.println(img + "");
                products.get(i).setProduct_picture(img);
            }
        }
        return products;
    }

    /**
     * @param
     * @return java.util.List<org.wlgzs.xf_mall.entity.Product>
     * @author 阿杰
     * @description 结算商品
     */
    @Override
    public List<ShoppingCart> findAllByIds(long[] Ids) {
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findAllByIds(Ids);
        String img;
        for (int i = 0; i < shoppingCarts.size(); i++) {
            if (shoppingCarts.get(i).getProduct_picture().contains(",")) {
                img = shoppingCarts.get(i).getProduct_picture();
                img = img.substring(0, img.indexOf(","));
                System.out.println("结算商品图片");
                shoppingCarts.get(i).setProduct_picture(img);
            }
        }
        return shoppingCarts;
    }

    //修改商品
    @Override
    public String edit(long productId, String product_details, MultipartFile[] myFileNames, HttpSession session,
                       HttpServletRequest request) {
        Product product = productRepository.findById(productId);
        if (product != null) {
            String realName = "";
            String[] str = new String[myFileNames.length];
            for (int i = 0; i < myFileNames.length; i++) {
                if (!myFileNames[i].getOriginalFilename().equals("")) {
                    String fileName = myFileNames[i].getOriginalFilename();
                    String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());

                    // 生成实际存储的真实文件名
                    realName = UUID.randomUUID().toString() + fileNameExtension;
                    // "/upload"是你自己定义的上传目录
                    String realPath = session.getServletContext().getRealPath("/upload");
                    File uploadFile = new File(realPath, realName);
                    System.out.println(uploadFile);
                    try {
                        myFileNames[i].transferTo(uploadFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (!myFileNames[i].getOriginalFilename().equals("")) {
                    str[i] = request.getContextPath() + "/upload/" + realName;
                }
            }
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < str.length; i++) {
                if (!myFileNames[i].getOriginalFilename().equals("")) {
                    stringBuffer.append(str[i] + ",");
                }
            }
            String product_picture = stringBuffer.substring(0, stringBuffer.length() - 1);
            Map<String, String[]> properties = request.getParameterMap();
            try {
                BeanUtils.populate(product, properties);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            product.setProduct_details(product_details);
            product.setProduct_picture(product_picture);
            productRepository.save(product);
            return "修改成功";
        } else {
            return "修改失败";
        }
    }

    //遍历所有分类  搜索分类
    @Override
    public Page getProductCategoryList(String category_name, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "categoryId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<ProductCategory> specification = new PageUtil<ProductCategory>(category_name).getPage("category_name");
        Page<ProductCategory> pages = productCategoryRepository.findAll(specification, pageable);
        return pages;
    }

    //遍历一级分类  不分页
    @Override
    public List<ProductCategory> findProductOneCategoryList() {
        return productCategoryRepository.findOneAll();
    }

    //遍历二级分类  不分页
    @Override
    public List<ProductCategory> findProductTwoCategoryList() {
        return productCategoryRepository.findTwoAll();
    }

    //通过一级分类查找分类名
    @Override
    public List<ProductCategory> findProductByOneCategory(String category) {
        return productCategoryRepository.findByCategoryParentName(category);
    }

    //通过二级分类查找商品
    @Override
    public List<Product> productByOneCategory(String category_name) {
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryParentNameAndTwo(category_name);
        ProductCategory[] toBeStored = productCategories.toArray(new ProductCategory[productCategories.size()]);
        String[] str = new String[toBeStored.length];
        for (int i = 0; i < toBeStored.length; i++) {
            str[i] = toBeStored[i].getCategory_name();
        }
        List<Product> products = productRepository.findProductByTwoCategory(str);//查询的当前页商品的集合
        return products;
    }

    //通过二级分类查找商品  分页
    @Override
    public Page<Product> findProductByTwoCategory(String product_category, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        //商品分类，活动，关键字，服务类型三种方式
        Specification<Product> specification = new PageUtil<Product>(product_category).getPage("product_category", "product_serviceType", "product_activity", "product_keywords");
        Page<Product> pages = productRepository.findAll(specification, pageable);
        return pages;
    }

    //通过多条件查询商品    分页
    //通过二级分类查找商品  不分页
    @Override
    public List<Product> findProductByCategory(String product_category) {
        return productRepository.findByCategory(product_category);
    }

    //增加一级分类
    @Override
    public void saveOne(ProductCategory productCategory, MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        String realName = "";
        if (myFileName != null) {
            String fileName = myFileName.getOriginalFilename();
            String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
            // 生成实际存储的真实文件名
            realName = UUID.randomUUID().toString() + fileNameExtension;
            // "/category"是你自己定义的上传目录
            String realPath = session.getServletContext().getRealPath("/category");
            File uploadFile = new File(realPath, realName);
            try {
                myFileName.transferTo(uploadFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        productCategory.setCategory_img(request.getContextPath() + "/category/" + realName);
        productCategory.setParent_name("0");
        productCategoryRepository.save(productCategory);
    }

    //增加二级分类
    @Override
    public void save(ProductCategory productCategory) {
        productCategory.setCategory_show(0);
        productCategory.setCategory_img("0");
        productCategoryRepository.save(productCategory);
    }

    //添加二级分类配件
    @Override
    public void saveTwo(ProductCategory productCategory) {
        productCategory.setCategory_show(2);
        productCategory.setCategory_img("0");
        productCategoryRepository.save(productCategory);
    }

    //删除分类
    @Override
    public String deleteCategory(long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId);
        if (productCategory != null) {
            if (productCategory.getParent_name().equals("0")) {
                List<ProductCategory> productCategories = productCategoryRepository.findByCategoryParentName(productCategory.getCategory_name());
                if (productCategories.size() != 0) {
                    long[] Ids = new long[productCategories.size()];
                    for (int i = 0; i < Ids.length; i++) {
                        Ids[i] = productCategories.get(i).getCategoryId();
                    }
                    productCategoryRepository.deleteByIds(Ids);
                }
            }
            productCategoryRepository.deleteById(categoryId);
            return "删除成功";
        }
        return "删除失败";
    }

    //修改分类
    @Override
    public void editCategory(long categoryId, MultipartFile myFileName, HttpSession session, HttpServletRequest request) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId);
        productCategory.setCategory_name(request.getParameter("category_name"));
        if (!productCategory.getParent_name().equals("0")) {
            productCategory.setParent_name(request.getParameter("parent_name"));
            if (productCategory.getCategory_show() == 2) {
                productCategory.setCategory_show(2);
                productCategory.setCategory_img("0");
            }
            if (productCategory.getCategory_show() != 2) {
                productCategory.setCategory_show(0);
                productCategory.setCategory_img("0");
            }
        }
        if (productCategory.getParent_name().equals("0")) {
            String realName = "";
            if (myFileName != null) {
                System.out.println("修改分类图片");
                String fileName = myFileName.getOriginalFilename();
                String fileNameExtension = fileName.substring(fileName.indexOf("."), fileName.length());
                // 生成实际存储的真实文件名
                realName = UUID.randomUUID().toString() + fileNameExtension;
                // "/category"是你自己定义的上传目录
                String realPath = session.getServletContext().getRealPath("/category");
                File uploadFile = new File(realPath, realName);
                try {
                    myFileName.transferTo(uploadFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            productCategory.setCategory_img(request.getContextPath() + "/category/" + realName);
            productCategory.setCategory_show(Integer.parseInt(request.getParameter("category_show")));
            productCategory.setParent_name("0");
        }
        productCategoryRepository.save(productCategory);
    }

    //按id查找类别
    @Override
    public ProductCategory findProductCategoryById(long categoryId) {
        return productCategoryRepository.findById(categoryId);
    }

    //添加购物车
    @Override
    public void save(long userId, long productId, int shoppingCart_count, HttpServletRequest request) {
        Product product = productRepository.findById(productId);

        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setShoppingCart_count(shoppingCart_count);
        ShoppingCart findShoppingCart = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (findShoppingCart != null) {
            findShoppingCart.setShoppingCart_count(findShoppingCart.getShoppingCart_count() + shoppingCart.getShoppingCart_count());
            shoppingCartRepository.save(findShoppingCart);
        }
        if (findShoppingCart == null) {
            shoppingCart.setProductId(productId);
            String img = null;
            if (product.getProduct_picture().contains(",")) {
                img = product.getProduct_picture();
                img = img.substring(0, img.indexOf(","));
            }
            shoppingCart.setProduct_picture(img);
            shoppingCart.setProduct_counterPrice(product.getProduct_counterPrice());
            shoppingCart.setProduct_keywords(product.getProduct_keywords());
            shoppingCart.setProduct_mallPrice(product.getProduct_mallPrice());
            shoppingCart.setProduct_specification(product.getProduct_specification());
            shoppingCart.setUserId(userId);
            shoppingCartRepository.save(shoppingCart);
        }
    }

    //添加收藏
    @Override
    public void saveCollection(long userId, long productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId);

        Collection collection = new Collection();
        Collection findCollection = collectionRepository.findByCollectionUserIdAndProductId(userId, productId);
        if (findCollection != null) {
            collectionRepository.deleteById(findCollection.getCollectionId());
            collectionRepository.save(findCollection);
        }
        if (findCollection == null) {
            collection.setProductId(productId);
            String img = null;
            if (product.getProduct_picture().contains(",")) {
                img = product.getProduct_picture();
                img = img.substring(0, img.indexOf(","));
            }
            collection.setProduct_picture(img);
            collection.setProduct_keywords(product.getProduct_keywords());
            collection.setProduct_mallPrice(product.getProduct_mallPrice());
            collection.setUserId(userId);
            collectionRepository.save(collection);
        }
    }

    //查找用户收藏是否存在
    @Override
    public Collection findByCollectionUserIdAndProductId(long userId, long productId) {
        return collectionRepository.findByCollectionUserIdAndProductId(userId, productId);
    }

    //用户的购物车
    @Override
    public List<ShoppingCart> findByUserIdCart(long userId) {
        return shoppingCartRepository.findByUserIdCart(userId);
    }

    //购物车移至收藏
    @Override
    public void moveToCollectionProduct(long shoppingCartId, long userId, long productId, HttpServletRequest request) {
        shoppingCartRepository.deleteById(shoppingCartId);
        Product product = productRepository.findById(productId);

        Map<String, String[]> properties = request.getParameterMap();
        Collection collection = new Collection();
        try {
            BeanUtils.populate(collection, properties);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        Collection findCollection = collectionRepository.findByCollectionUserIdAndProductId(userId, productId);
        if (findCollection != null) {
            collectionRepository.deleteById(findCollection.getCollectionId());
            collectionRepository.save(findCollection);
        }
        if (findCollection == null) {
            collection.setProductId(productId);
            String img = null;
            if (product.getProduct_picture().contains(",")) {
                img = product.getProduct_picture();
                img = img.substring(0, img.indexOf(","));
            }
            collection.setProduct_picture(img);
            collection.setProduct_keywords(product.getProduct_keywords());
            collection.setProduct_mallPrice(product.getProduct_mallPrice());
            collection.setUserId(userId);
            System.out.println();
            collectionRepository.save(collection);
        }
    }

    //删除购物车
    @Override
    public void deleteShoppingCart(long shoppingCartId) {
        shoppingCartRepository.deleteById(shoppingCartId);
    }

    //批量删除购物车
    @Override
    public void deleteShoppingCarts(String shoppingCartIds) {
        IdsUtil idsUtil = new IdsUtil();
        long[] Ids = idsUtil.IdsUtils(shoppingCartIds);
        shoppingCartRepository.deleteByIds(Ids);
    }

    //修改购物车数量
    @Override
    public void changeShoppingCarCount(long shopping_cart_id, int shopping_cart_count) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(shopping_cart_id);
        shoppingCart.setShoppingCart_count(shopping_cart_count);
        shoppingCartRepository.save(shoppingCart);
    }

    //用户的收藏
    @Override
    public List<Collection> findByUserIdCollection(long userId) {
        return collectionRepository.findByUserIdCollection(userId);
    }

    //删除收藏
    @Override
    public void deleteCollection(long collectionId) {
        collectionRepository.deleteById(collectionId);
    }

    //批量删除收藏
    @Override
    public void deleteCollections(String collectionId) {
        IdsUtil idsUtil = new IdsUtil();
        long[] Ids = idsUtil.IdsUtils(collectionId);
        collectionRepository.deleteByIds(Ids);
    }

    //搜索收藏商品
    @Override
    public List<Collection> findCollections(String product_keywords, long userId) {
        return collectionRepository.findCollections(product_keywords, userId);
    }

    //搜索提示
    @Override
    public List<ProductCategory> findProductByWord(String category_name) {
        return productCategoryRepository.findProductByWord(category_name);
    }

    //积分商品展示
    @Override
    public Page<Product> findByProduct_isRedeemable(int product_isRedeemable, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.ASC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<Product> specification = new Specification<Product>() {
            @Override
            public Predicate toPredicate(Root<Product> root,
                                         CriteriaQuery<?> criteriaQuery,
                                         CriteriaBuilder criteriaBuilder) {
                Predicate predicate = criteriaBuilder.equal(root.get("product_isRedeemable"), product_isRedeemable);
                return criteriaBuilder.and(predicate);
            }
        };
        return productRepository.findAll(specification, pageable);
    }

    //商品推荐
    @Override
    public List<Product> recommendedByUserId(long userId) {
        //通过用户id查询最新足迹商品
        List<Footprint> footprints = footprintRepository.recommendedByUserId(userId);
        System.out.println(footprints);
        List<Product> products = new ArrayList<Product>();
        if (footprints != null && footprints.size() != 0) {
            long[] productIds = new long[footprints.size()];
            for (int i = 0; i < footprints.size(); i++) {
                productIds[i] = footprints.get(i).getProductId();
            }
            //通过足迹商品id查询商品
            List<Product> productsOne = productRepository.findProductByProductId(productIds);
            List<String> productCategories = new ArrayList<String>();
            for (int i = 0; i < productsOne.size(); i++) {
                productCategories.add(productsOne.get(i).getProduct_category());
            }
            String[] category_names = productCategories.toArray(new String[productCategories.size()]);
            //通过商品二级分类查询一级分类
            List<ProductCategory> productCategoryList = productCategoryRepository.findOneCategoryByCategoryName(category_names);
            List<String> oneCategoryList = new ArrayList<String>();
            for (int i = 0; i < productCategoryList.size(); i++) {
                oneCategoryList.add(productCategoryList.get(i).getParent_name());
            }
            //去重 利用set顺序不变
            Set<String> set = new HashSet<>();
            List<String> newOneCategoryList = new ArrayList<String>();
            for (String cd : oneCategoryList) {
                if (set.add(cd)) {
                    newOneCategoryList.add(cd);
                }
            }

            //查询用户的订单
            List<Orders> orders = ordersRepository.userOrderList(userId);
            System.out.println(orders);
            List<String> productOneCategoriesTwo = new ArrayList<String>();
            if (orders != null && orders.size() != 0) {
                Date data = new Date();
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd :hh:mm:ss");
                //将近半年的订单放在一个集合中
                List<Orders> ordersList = new ArrayList<Orders>();
                for (int i = 0; i < orders.size(); i++) {
                    double between = (double) (data.getTime() - orders.get(i).getOrder_purchaseTime().getTime()) / (double) (1000 * 60 * 60 * 24);
                    if (between < 180) {
                        ordersList.add(orders.get(i));
                    }
                }
                //将近半年订单的商品id放在一个数组里
                long[] orderProductId = new long[ordersList.size()];
                for (int i = 0; i < ordersList.size(); i++) {
                    double between = (double) (data.getTime() - orders.get(i).getOrder_purchaseTime().getTime()) / (double) (1000 * 60 * 60 * 24);
                    if (between < 180) {
                        orderProductId[i] = ordersList.get(i).getProductId();
                    }
                }
                //通过商品id查询商品
                List<Product> orderProducts = productRepository.findProductByProductId(orderProductId);
                //把商品的分类放在一个集合中
                List<String> categoryOne = new ArrayList<String>();
                for (int i = 0; i < orderProducts.size(); i++) {
                    categoryOne.add(orderProducts.get(i).getProduct_category());
                }
                String[] orderCategory_names = categoryOne.toArray(new String[categoryOne.size()]);
                //通过商品二级分类查询一级分类
                List<ProductCategory> orderProductCategoryList = productCategoryRepository.findOneCategoryByCategoryName(orderCategory_names);
                List<String> orderOneCategoryList = new ArrayList<String>();
                for (int i = 0; i < orderProductCategoryList.size(); i++) {
                    orderOneCategoryList.add(orderProductCategoryList.get(i).getParent_name());
                }
                //去重 利用set顺序不变
                Set<String> orderSet = new HashSet<String>();
                List<String> orderNewOneCategoryList = new ArrayList<String>();
                for (String cd : orderOneCategoryList) {
                    if (orderSet.add(cd)) {
                        orderNewOneCategoryList.add(cd);
                    }
                }
                //查询该一级分类下的配件
                String[] parent_names = orderOneCategoryList.toArray(new String[orderOneCategoryList.size()]);
                List<ProductCategory> productOneCategories = productCategoryRepository.findCategoryByParentNameTwo(parent_names);
                if (productOneCategories != null) {
                    for (int i = 0; i < productOneCategories.size(); i++) {
                        productOneCategoriesTwo.add(productOneCategories.get(i).getCategory_name());
                    }
                }
                //从所推荐的一级分类中 去除 半年内订单商品分类对应的一级分类
                Iterator<String> it = newOneCategoryList.iterator();
                while (it.hasNext()) {
                    String x = it.next();
                    for (int j = 0; j < orderNewOneCategoryList.size(); j++) {
                        if (x.equals(orderNewOneCategoryList.get(j))) {
                            it.remove();
                        }
                    }
                }
            }

            if (newOneCategoryList.size() != 0) {
                String[] parent_names = newOneCategoryList.toArray(new String[newOneCategoryList.size()]);
                //通过一级分类查询二级分类
                List<ProductCategory> productOneCategories = productCategoryRepository.findCategoryByParentName(parent_names);
                List<String> categories = new ArrayList<String>();
                for (int i = 0; i < productOneCategories.size(); i++) {
                    categories.add(productOneCategories.get(i).getCategory_name());
                }
                if (productOneCategoriesTwo.size() != 0) {
                    categories.addAll(productOneCategoriesTwo);
                }
                String[] product_categories = categories.toArray(new String[categories.size()]);
                //通过二级分类查询商品
                products = productRepository.findProductByTwoCategory(product_categories);
                String img;
                for (int i = 0; i < products.size(); i++) {
                    if (products.get(i).getProduct_picture().contains(",")) {
                        img = products.get(i).getProduct_picture();
                        img = img.substring(0, img.indexOf(","));
                        products.get(i).setProduct_picture(img);
                    }
                }
            }
        }
        return products;
    }

    //主页商品数据
    @Override
    public List<Product> homeProductList(List<ProductCategory> productOneCategories) {
        List<Product> products = new ArrayList<Product>();
        List<Product> productsOne = productOneCategory(productOneCategories.get(0).getCategory_name());//主页部分第一分类商品
        for (int i = 0; i < productsOne.size(); i++) {
            productsOne.get(i).setProduct_category(productOneCategories.get(0).getCategory_name());
        }
        products.addAll(productsOne);
        List<Product> productsTwo = productOneCategory(productOneCategories.get(1).getCategory_name());//主页部分第二分类商品
        for (int i = 0; i < productsTwo.size(); i++) {
            productsTwo.get(i).setProduct_category(productOneCategories.get(1).getCategory_name());
        }
        products.addAll(productsTwo);
        List<Product> productsThree = productOneCategory(productOneCategories.get(2).getCategory_name());//主页部分第三分类商品
        for (int i = 0; i < productsThree.size(); i++) {
            productsThree.get(i).setProduct_category(productOneCategories.get(2).getCategory_name());
        }
        products.addAll(productsThree);
        List<Product> productsFour = productOneCategory(productOneCategories.get(3).getCategory_name());
        //主页部分第四分类商品
        for (int i = 0; i < productsFour.size(); i++) {
            productsFour.get(i).setProduct_category(productOneCategories.get(3).getCategory_name());
        }
        products.addAll(productsFour);
        List<Product> productsFive = productOneCategory(productOneCategories.get(4).getCategory_name());//主页部分第五分类商品
        for (int i = 0; i < productsFive.size(); i++) {
            productsFive.get(i).setProduct_category(productOneCategories.get(4).getCategory_name());
        }
        products.addAll(productsFive);
        String img;
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getProduct_picture().contains(",")) {
                img = products.get(i).getProduct_picture();
                img = img.substring(0, img.indexOf(","));
                products.get(i).setProduct_picture(img);
            }
        }
        return products;
    }

    //主页最新商品
    @Override
    public List<Product> getProductList() {
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        return productRepository.getProductList(sort);
    }

    //搜索商品
    @Override
    public Page<Product> searchProduct(HttpServletRequest request, String product_category, int page, int limit) throws IOException {
        String path = request.getSession().getServletContext().getRealPath("/");
        String pathTwo = path + "txtFile/" + product_category;
        File dir = new File(pathTwo);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        IdsUtil idsUtil = new IdsUtil();
        idsUtil.writerFile(product_category, pathTwo + "/" + product_category + ".txt");

        //System.out.println(pathTwo);
        Map<String, HashMap<String, Integer>> normal = ReadFiles.NormalTFOfAll(pathTwo);
        Map<String, Float> idf = ReadFiles.idf(pathTwo);
        List<String> stringList = new ArrayList<String>();
        for (String word : idf.keySet()) {
            stringList.add(word);
            System.out.println(word);
        }
        File file = new File(pathTwo + "/" + product_category + ".txt");
        if (file.exists() && file.isFile()) {
            file.delete();
        }
        File fileTwo = new File(pathTwo);
        if (fileTwo.exists() && fileTwo.isDirectory()) {
            fileTwo.delete();
        }
        String[] product_categories = stringList.toArray(new String[stringList.size()]);
        //通过二级分类查询商品
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        System.out.println(page+"-------"+limit);
        System.out.println(pageable);
        /*List<Product> productList = new ArrayList<>();
        for (int i = 0; i < product_categories.length; i++) {
            productList.addAll(productRepository.findProductByProductKeywords(product_categories[i]));
        }*/
        PageUtilTwo pageUtilTwo = new PageUtilTwo();
        Specification<Product> specification = pageUtilTwo.getPage(product_categories);
        Page<Product> pages = productRepository.findAll(specification,pageable);
        //Page<Product> pages = new PageImpl<>(productList,pageable,productList.size());
        System.out.println("getContent:"+pages.getContent());
        System.out.println("getTotalPages:"+pages.getTotalPages());
        System.out.println("getNumber:"+pages.getNumber());
        System.out.println("getTotalElements:"+pages.getTotalElements());
        System.out.println("getNumberOfElements:"+pages.getNumberOfElements());
        System.out.println("getPageable:"+pages.getPageable());
        System.out.println("getSort:"+pages.getSort());
        System.out.println("getSize:"+pages.getSize());
        System.out.println("getClass:"+pages.getClass());
        return pages;
    }

    private List<Product> productOneCategory(String category_name) {
        List<ProductCategory> productCategories = productCategoryRepository.findByCategoryParentNameAndTwo(category_name);
        ProductCategory[] toBeStored = productCategories.toArray(new ProductCategory[productCategories.size()]);
        String[] str = new String[toBeStored.length];
        for (int i = 0; i < toBeStored.length; i++) {
            str[i] = toBeStored[i].getCategory_name();
        }
        return productRepository.findProductByTwoCategory(str);//查询的当前页商品的集合
    }

}
