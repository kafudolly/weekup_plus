package Middleware;

import homework.file.huffman.HuffmanTree;
import log.Login;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;

public class FileTools {
    private static final long serialVersionUID = 1L;
    // 上传配置
    private static final int MEMORY_THRESHOLD   = 1024 * 1024 * 3;  // 3MB
    private static final int MAX_FILE_SIZE      = 1024 * 1024 * 40; // 40MB
    private static final int MAX_REQUEST_SIZE   = 1024 * 1024 * 50; // 50MB

    public static void DirectoryDelete(File file) {
        //to end the recursive loop
        if (!file.exists())
            return;

        //if directory, go inside and call recursively
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                //call recursively
                DirectoryDelete(f);
            }
        }
        //call delete to delete files and empty directory
        file.delete();
    }

    static public String UploadFile(HttpServletRequest req, String uploadPath) {
        if (!ServletFileUpload.isMultipartContent(req)) {
            return null;
        }
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(MEMORY_THRESHOLD);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));

        ServletFileUpload upload = new ServletFileUpload(factory);

        // 设置最大文件上传值
        upload.setFileSizeMax(MAX_FILE_SIZE);

        // 设置最大请求值 (包含文件和表单数据)
        upload.setSizeMax(MAX_REQUEST_SIZE);

        upload.setHeaderEncoding("UTF-8");

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }

        try {
            List<FileItem> formItems = upload.parseRequest(req);
            if (formItems != null && formItems.size() > 0) {
                // 迭代表单数据
                for (FileItem item : formItems) {
                    if (!item.isFormField()) {
                        String fileName = new File(item.getName()).getName();
                        HuffmanTree fileTree = new HuffmanTree();
                        File storeFile = new File(fileName);
                        // 保存文件到硬盘
                        item.write(storeFile);
                        fileTree.HuffmanCompress(storeFile, uploadPath+"/"+fileName);
                        storeFile.delete();
                        return fileName;
                    }
                }
            }
        } catch (Exception ex) {

            return null;
        }
        return null;
    }

    static public void downloadFile(HttpServletResponse resp, File f) throws IOException {
        File tempFile = new File(f.getName());
        tempFile.createNewFile();
        new HuffmanTree().HuffmanDecompress(f, tempFile);
        resp.setHeader("content-disposition","attachment;filename="
                + URLEncoder.encode(f.getName(),"utf-8"));
        //获取要下载资源的目录的字节输入流对象
        FileInputStream is = new FileInputStream(tempFile);
        //获取字符输出流
        ServletOutputStream os = resp.getOutputStream();
        //拷贝
        IOUtils.copy(is,os);
        //关流
        is.close();
        os.close();
        tempFile.delete();
    }
}