package controllers;

import play.mvc.*;
import play.db.jpa.*;

import views.html.*;
import play.data.FormFactory;
import play.data.Form;
import models.UploadedScan;
import javax.inject.Inject;

import java.io.File;
import java.nio.file.Files;
import java.io.ByteArrayInputStream;

import java.util.List;

import static play.libs.Json.*;

/**
 * This controller contains an action to handle HTTP requests
 * to the application's form page.
 */
public class FormController extends Controller {
    
    @Inject
    FormFactory formFactory;
    
    /**
     * An action that renders an HTML page with a welcome message.
     * The configuration in the <code>routes</code> file means that
     * this method will be called when the application receives a
     * <code>GET</code> request with a path of <code>/</code>.
     */
    public Result renderForm() {
        //return ok(index.render("Your new application is ready. GET"));
        //UploadedScan us = new UploadedScan();
        
        return ok(form.render());
    }
    
    @Transactional
    public Result uploadFile() {
        Form<UploadedScan> usForm = formFactory.form(UploadedScan.class);
        UploadedScan us = usForm.bindFromRequest().get();
        
        play.mvc.Http.MultipartFormData<File> body = request().body().asMultipartFormData();
        play.mvc.Http.MultipartFormData.FilePart<File> picture = body.getFile("scan");
        if (picture != null) {
            
            String contentType = picture.getContentType();
            us.setContentType(contentType);
            java.io.File file = picture.getFile();
            
            try {
                us.setScanFile(Files.readAllBytes(file.toPath()));
            } catch(Exception e){
                e.printStackTrace();
            }
            
            String fileName = picture.getFilename();
            String extension = "";
            String newName;
            
            // getting epoch in milliseconds
            Long milliseconds = System.currentTimeMillis();
            
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i+1);
                newName = fileName.substring(0, i);
            } else {
                newName = fileName;
                extension = "";
            }
            newName += "_" + String.valueOf(milliseconds);
            if (!"".equals(extension)){
                newName += "." + extension;
            }
            us.setScanFileName(newName);
            JPA.em().persist(us);
            
            return ok(index.render("Your scan has been uploaded. Assigned ID [" + us.getId() + "]"));
        } else {
            // No scan in this POST request
            return ok(index.render("Something wrong with the scan."));
        }
    }
    
    @Transactional(readOnly = true)
    public Result getImage(Long id) {
        UploadedScan entity = JPA.em().find(UploadedScan.class, id);
        ByteArrayInputStream input = null;
        String contType = entity.getContentType();
        byte[] byteArray = entity.getScanFile();
        try {
            input = new ByteArrayInputStream(byteArray);
        } catch (Exception e) {
            
        }
        
        return ok(input).as(contType);
    }
    
    @Transactional(readOnly = true)
    public Result getUploadedScans() {
        List<UploadedScan> scans = (List<UploadedScan>) JPA.em().createQuery("from UploadedScan").getResultList();
        
        return ok(view_all.render("Complete list of uploaded scans:", scans));
        
    }
}
