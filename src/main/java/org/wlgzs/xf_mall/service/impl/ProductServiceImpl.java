package org.wlgzs.xf_mall.service.impl;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.wlgzs.xf_mall.dao.CollectionRepository;
import org.wlgzs.xf_mall.dao.ProductCategoryRepository;
import org.wlgzs.xf_mall.dao.ProductRepository;
import org.wlgzs.xf_mall.dao.ShoppingCartRepository;
import org.wlgzs.xf_mall.entity.Collection;
import org.wlgzs.xf_mall.entity.Product;
import org.wlgzs.xf_mall.entity.ProductCategory;
import org.wlgzs.xf_mall.entity.ShoppingCart;
import org.wlgzs.xf_mall.service.ProductService;
import org.wlgzs.xf_mall.util.IdsUtil;
import org.wlgzs.xf_mall.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

    //分页遍历商品  搜索商品
    @Override
    public Page<Product> getProductListPage(String product_keywords, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<Product> specification = new PageUtil<Product>(product_keywords).getPage("product_keywords");
        Page pages = productRepository.findAll(specification, pageable);
        return pages;
    }

    //遍历所有商品
    @Override
    public List<Product> getProductList() {
        return productRepository.findAll();
    }

    //添加商品
    @Override
    public void saveProduct(String product_details, MultipartFile[] myFileNames, HttpSession session, HttpServletRequest request) {
        String realName = "";
        System.out.println(myFileNames.length);
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
    public void delete(long productId, HttpServletRequest request) {
        Product product = productRepository.findById(productId);
        String path = request.getSession().getServletContext().getRealPath("/");
        String image = product.getProduct_picture();

        String[] img = image.split(",");
        for (int i = 0; i < img.length; i++) {
            File file = new File(path+""+img[i].substring(1,img[0].length()));
            if (file.exists() && file.isFile()) {
                file.delete();
            }
        }
        productRepository.deleteById(productId);
    }

    //通过id查找商品
    @Override
    public Product findProductById(long productId) {
        /*String img;
        if (product.getProduct_picture().contains(",")) {
            img = product.getProduct_picture();
            img = img.substring(0, img.indexOf(","));
            product.setProduct_picture(img);
        }*/
        return  productRepository.findById(productId);
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
     * @param [Ids]
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
    public void edit(long productId, String product_details, MultipartFile[] myFileNames, HttpSession session,
                     HttpServletRequest request) {
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
        Product product = productRepository.findById(productId);
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
    }

    //遍历所有分类  搜索分类
    @Override
    public Page getProductCategoryList(String category_name, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "categoryId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<ProductCategory> specification = new PageUtil<ProductCategory>(category_name).getPage("category_name");
        Page pages = productCategoryRepository.findAll(specification, pageable);
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
    public List<ProductCategory> findProductByOneCategory(String category, int page, int limit) {
        return productCategoryRepository.findByCategoryParentName(category);
    }

    //通过二级分类查找商品  分页
    @Override
    public Page<Product> findProductByTwoCategory(String product_category, int page, int limit) {
        Sort sort = new Sort(Sort.Direction.DESC, "productId");
        Pageable pageable = new PageRequest(page, limit, sort);
        Specification<Product> specification = new PageUtil<Product>(product_category).getPage("product_category");
        Page pages = productRepository.findAll(specification, pageable);
        return pages;
    }

    //通过二级分类查找商品  不分页
    @Override
    public List<Product> findProductByCategory(String product_category) {
        return productRepository.findByCategory(product_category);
    }

    //增加一级分类
    @Override
    public void saveOne(ProductCategory productCategory) {
        productCategory.setParent_name("0");
        productCategoryRepository.save(productCategory);
    }

    //增加二级分类
    @Override
    public void save(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    //删除分类
    @Override
    public void deleteCategory(long categoryId) {
        ProductCategory productCategory = productCategoryRepository.findById(categoryId);
        if(productCategory.getParent_name().equals("0")){
            List<ProductCategory> productCategories = productCategoryRepository.findByCategoryParentName(productCategory.getCategory_name());
            System.out.println(productCategories);

            System.out.println(productCategories.size()!=0);
            if(productCategories.size()!=0){
                long[] Ids = new long[productCategories.size()];
                for (int i = 0; i < Ids.length; i++) {
                    Ids[i] = productCategories.get(i).getCategoryId();
                }
                productCategoryRepository.deleteByIds(Ids);
            }
        }
        productCategoryRepository.deleteById(categoryId);
    }

    //修改分类
    @Override
    public void editCategory(ProductCategory productCategory) {
        productCategoryRepository.save(productCategory);
    }

    //按id查找类别
    @Override
    public ProductCategory findProductCategoryById(long categoryId) {
        return productCategoryRepository.findById(categoryId);
    }

    //添加购物车
    @Override
    public void save(long userId, long productId,int shoppingCart_count, HttpServletRequest request) {
        Product product = productRepository.findById(productId);

        ShoppingCart shoppingCart = new ShoppingCart();
        /*int count = Integer.parseInt(request.getParameter("shoppingCart_count"));*/
        System.out.println(shoppingCart_count+"---");
        shoppingCart.setShoppingCart_count(shoppingCart_count);
        ShoppingCart findShoppingCart = shoppingCartRepository.findByUserIdAndProductId(userId, productId);
        if (findShoppingCart != null) {
            findShoppingCart.setShoppingCart_count(findShoppingCart.getShoppingCart_count() + shoppingCart.getShoppingCart_count());
            shoppingCartRepository.save(findShoppingCart);
        }
        if (findShoppingCart == null) {
            shoppingCart.setProductId(productId);
            shoppingCart.setProduct_picture(product.getProduct_picture());
            shoppingCart.setProduct_counterPrice(product.getProduct_counterPrice());
            shoppingCart.setProduct_keywords(product.getProduct_keywords());
            shoppingCart.setProduct_mallPrice(product.getProduct_mallPrice());
            shoppingCart.setProduct_specification(product.getProduct_specification());
            shoppingCart.setUserId(userId);
            shoppingCartRepository.save(shoppingCart);
        }
    }

    //查找用户购物车是否存在
    @Override
    public ShoppingCart findByUserIdAndProductId(long userId, long productId) {
        return shoppingCartRepository.findByUserIdAndProductId(userId, productId);
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
            collection.setProduct_picture(product.getProduct_picture());
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
        List<ShoppingCart> shoppingCarts = shoppingCartRepository.findByUserIdCart(userId);
        String img;
        for (int i = 0; i < shoppingCarts.size(); i++) {
            if (shoppingCarts.get(i).getProduct_picture().contains(",")) {
                img = shoppingCarts.get(i).getProduct_picture();
                img = img.substring(0, img.indexOf(","));
                System.out.println(img);
                shoppingCarts.get(i).setProduct_picture(img);
            }
        }
        return shoppingCarts;
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
            collection.setProduct_picture(product.getProduct_picture());
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
        List<Collection> collections = collectionRepository.findByUserIdCollection(userId);
        String img;
        for (int i = 0; i < collections.size(); i++) {
            if (collections.get(i).getProduct_picture().contains(",")) {
                img = collections.get(i).getProduct_picture();
                img = img.substring(0, img.indexOf(","));
                System.out.println(" ");
                collections.get(i).setProduct_picture(img);
            }
        }
        return collections;
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
        return collectionRepository.findCollections(product_keywords,userId);
    }

    //搜索提示
    @Override
    public List<ProductCategory> findProductByWord(String category_name) {
        return productCategoryRepository.findProductByWord(category_name);
    }

    //积分商品展示
    @Override
    public List<Product> findByProduct_isRedeemable() {
        return productRepository.findByProduct_isRedeemable();
    }

}
