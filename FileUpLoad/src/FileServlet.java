import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

public class FileServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        //1.获取输入流解析器工厂
        DiskFileItemFactory factory = new DiskFileItemFactory();
        //2.通过工厂获得输入流解析器
        ServletFileUpload fileUpload = new ServletFileUpload(factory);
        //设置单个文件的大小字节上限为10240字节
        fileUpload.setFileSizeMax(20240000);
        //设置表单单次提交的所有表单总的数据大小字节数上限为1024000字节
        fileUpload.setSizeMax(1024000000);
        //3.利用解析器开始解析request中的字节输入流
        try {
            //从输入流中取出所有的input标签得到集合
            List<FileItem> fileItems = fileUpload.parseRequest(request);
            //循环分别得到集合中的每一个对象
            for (FileItem item:fileItems) {
                if(item.isFormField()){//如果是普通input标签
                    String fieldName = item.getFieldName();//得到文件名称
                    String value = item.getString("utf-8");
                }else {//如果不是普通标签（即file标签）
                    String fname = item.getName();//得到文件名称
                    //设置一个路径在发布文件夹下新建一个uplads文件夹
                    String path = request.getServletContext().getRealPath("/WEB-INF/uploads");
                    //通过路径和文件名新建一个指向该路径的文件对象
                    File file = new File(path, fname);
                    item.write(file);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       //读取图片===>送给浏览器===>从预览到下载
        //1.获取需要下载的文件名称；
        String fname = request.getParameter("fname");
        //获取文件在磁盘中的路径
        String path = request.getServletContext().getRealPath("/WEB-INF/uploads");
        //通过文件名和文件路径构建文件对象
        File file = new File(path, fname);
        //创建输入流并把文件读取到输入流中
        FileInputStream inputStream = new FileInputStream(file);
        //设置下载相应头
        fname = new String(fname.getBytes("utf-8"),"iso8859-1");
        response.addHeader("Content-Disposition","attachment;filename="+fname);
        //创建输出流
        ServletOutputStream outputStream = response.getOutputStream();
        //把输入流中的数据复制到输出流中相应到浏览器
        IOUtils.copy(inputStream, outputStream);

    }
}
