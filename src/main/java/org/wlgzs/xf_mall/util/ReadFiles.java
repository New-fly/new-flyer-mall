package org.wlgzs.xf_mall.util;

import jeasy.analysis.MMAnalyzer;

import java.io.*;
import java.util.*;

public class ReadFiles {

    private static List<String> fileList = new ArrayList<>();
    private static HashMap<String, HashMap<String, Integer>> allTheNormalTF = new HashMap<>();

    private static List<String> readDirs(String filepath) {
        File file = new File(filepath);
        if (!file.isDirectory()) {
            System.out.println("输入的参数应该为[文件夹名]");
            System.out.println("filepath: " + file.getAbsolutePath());
        } else {
            file.isDirectory();
            String[] filelist = file.list();
            assert filelist != null;
            for (int i = 0; i < filelist.length; i++) {
                File readfile = new File(filepath , filelist[i]);
                if (!readfile.isDirectory()) {
                    for (int j = 0; j < fileList.size(); j++) {
                        fileList.remove(i);
                    }
                    fileList.add(readfile.getAbsolutePath());
                } else {
                    readfile.isDirectory();
                    readDirs(filepath + "/" + filelist[i]);
                }
            }
        }
        return fileList;
    }

    private static String readFiles(String file) throws IOException {
        StringBuilder sb = new StringBuilder();
        InputStreamReader is = new InputStreamReader(new FileInputStream(file), "gbk");
        BufferedReader br = new BufferedReader(is);
        String line = br.readLine();
        while (line != null) {
            sb.append(line).append("\r\n");
            line = br.readLine();
        }
        br.close();
        return sb.toString();
    }

    private static String[] cutWord(String file) throws IOException {
        String[] cutWordResult;
        String text = ReadFiles.readFiles(file);
        MMAnalyzer analyzer = new MMAnalyzer();
        String tempCutWordResult = analyzer.segment(text, " ");
        cutWordResult = tempCutWordResult.split(" ");
        return cutWordResult;
    }

    public static HashMap<String, Float> tf(String[] cutWordResult) {
        HashMap<String, Float> tf = new HashMap<>();//正规化
        int wordNum = cutWordResult.length;
        int wordtf;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            for (int j = 0; j < wordNum; j++) {
                if (!cutWordResult[i].equals(" ") && i != j) {
                    if (cutWordResult[i].equals(cutWordResult[j])) {
                        cutWordResult[j] = " ";
                        wordtf++;
                    }
                }
            }
            if (!cutWordResult[i].equals(" ")) {
                tf.put(cutWordResult[i], ((float) ++wordtf) / wordNum);
                cutWordResult[i] = " ";
            }
        }
        return tf;
    }

    private static HashMap<String, Integer> normalTF(String[] cutWordResult) {
        HashMap<String, Integer> tfNormal = new HashMap<>();//没有正规化
        int wordNum = cutWordResult.length;
        int wordtf;
        for (int i = 0; i < wordNum; i++) {
            wordtf = 0;
            if (!cutWordResult[i].equals(" ")) {
                for (int j = 0; j < wordNum; j++) {
                    if (i != j) {
                        if (cutWordResult[i].equals(cutWordResult[j])) {
                            cutWordResult[j] = " ";
                            wordtf++;
                        }
                    }
                }
                tfNormal.put(cutWordResult[i], ++wordtf);
                cutWordResult[i] = " ";
            }
        }
        return tfNormal;
    }

    public static void NormalTFOfAll(String dir) throws IOException {
        List<String> fileList = ReadFiles.readDirs(dir);
        for (String s : fileList) {
            HashMap<String, Integer> dict;
            for (Iterator<Map.Entry<String, HashMap<String, Integer>>> it = allTheNormalTF.entrySet().iterator(); it.hasNext(); ) {
                it.next();
                it.remove();
            }
            dict = ReadFiles.normalTF(ReadFiles.cutWord(s));
            allTheNormalTF.put(s, dict);
        }
    }

    public static Map<String, Float> idf() {
        //公式IDF＝log((1+|D|)/|Dt|)，其中|D|表示文档总数，|Dt|表示包含关键词t的文档数量。
        Map<String, Float> idf = new HashMap<>();
        List<String> located = new ArrayList<>();
        float Dt;
        float D = allTheNormalTF.size();//文档总数
        List<String> key = fileList;//存储各个文档名的List
        Map<String, HashMap<String, Integer>> tfInIdf = allTheNormalTF;//存储各个文档tf的Map

        for (int i = 0; i < D; i++) {
            HashMap<String, Integer> temp = tfInIdf.get(key.get(i));
            for (String word : temp.keySet()) {
                Dt = 1;
                if (!(located.contains(word))) {
                    for (int k = 0; k < D; k++) {
                        if (k != i) {
                            HashMap<String, Integer> temp2 = tfInIdf.get(key.get(k));
                            if (temp2.keySet().contains(word)) {
                                located.add(word);
                                Dt = Dt + 1;
                            }
                        }
                    }
                    idf.put(word, Log.log((1 + D) / Dt, 10));
                }
            }
        }
        return idf;
    }

}