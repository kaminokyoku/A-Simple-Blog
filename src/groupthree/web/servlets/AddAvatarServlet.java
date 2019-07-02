package groupthree.web.servlets;

import util.JSONResponse;
import groupthree.web.servlets.UpImage;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.simple.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class AddAvatarServlet extends HttpServlet {
    private File uploadsFolder; // The folder where article images should be uploaded
    private File tempFolder; // The temp folder required by the file-upload logic

    /**
     * Initializes the uploadsFolder and tempFolder, and makes sure they exist.
     * <p>
     * Remember, in IntelliJ, when running locally, these folders will be somewhere in the "out" directory.
     * <p>
     * When deployed, they will be somewhere on the server, depending on the server's configuration.
     */
    @Override
    public void init() throws ServletException {
        super.init();

        // Get the upload folder, ensure it exists.
        this.uploadsFolder = new File(getServletContext().getRealPath("/avatar-pics/user-avatar"));
        System.out.println(uploadsFolder.getPath());
        if (!uploadsFolder.exists()) {
            uploadsFolder.mkdirs();
        }
        // Create the temporary folder that the file-upload mechanism needs.
        this.tempFolder = new File(getServletContext().getRealPath("/WEB-INF/temp"));
        if (!tempFolder.exists()) {
            tempFolder.mkdirs();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set up file upload mechanism
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setRepository(tempFolder);
        ServletFileUpload upload = new ServletFileUpload(factory);

        try {
            List<FileItem> fileItems = upload.parseRequest(req);
            for (FileItem fi : fileItems) {
                File avatarFile = new File(this.uploadsFolder, fi.getName());
                UpImage upImage = new UpImage();
                upImage.setFileName(fi.getName());

                fi.write(avatarFile);
                System.out.println("write:" + avatarFile.getPath());

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("uploadedAvatar",upImage.getFileName());
                JSONResponse.send(resp,jsonObject);

            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}
