package ru.horoshiki.crm.site.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ru.horoshiki.crm.site.model.dto.BackendData;
import ru.horoshiki.crm.site.model.entity.User;
import ru.horoshiki.crm.site.service.UserService;
import ru.horoshiki.crm.site.utils.files.FilePathUtils;
import ru.horoshiki.crm.site.utils.files.FileUtils;

import javax.imageio.ImageIO;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Created by onyushkindv on 09.12.2016.
 */

@Controller("file-controller")
@RequestMapping("/upload")
public class FileUploaderController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/file", method = RequestMethod.POST)
    public
    @ResponseBody
    BackendData editPassword(HttpServletRequest request, @RequestParam("file") MultipartFile[] files) throws Exception {

        User user = userService.getUserByLogin(SecurityContextHolder.getContext().getAuthentication().getName());

        MultipartFile file = files[0];

        String RESOURCE_PATH = "/var/www/html/my.techbe.ru/";
        String urlFolderPath = FilePathUtils.getImagePath(RESOURCE_PATH);
        String urlImage = FilePathUtils.getImageFileName(file.getOriginalFilename(), file.getSize(), 0);

        BufferedImage originalImage  = ImageIO.read(file.getInputStream());
        FileUtils fu = new FileUtils();
////        fu.save(originalImage, "d://".concat(urlFolderPath.concat("/")).concat(urlImage));
//        fu.save(originalImage, RESOURCE_PATH.concat("/").concat(urlFolderPath).concat("/").concat(urlImage));
        String urlAvatar = urlFolderPath.concat("/").concat(urlImage);



        BufferedImage bfSmall = null;
        if(originalImage.getWidth()<=originalImage.getHeight()) {
            bfSmall = fu.widthResize(originalImage, 500);
            bfSmall = fu.cropImage(bfSmall, 0, 0, 500, 500);
        }else{
            float persentStandart =  (float)500/(float)500;
            float persentOriginal = (float)originalImage.getWidth()/(float)originalImage.getHeight();
            float persent = Math.abs(persentStandart-persentOriginal+1);
            if(persent<1){
                persent=1;
            }
            bfSmall = fu.heightResize(originalImage, (int)(500*persent));
            bfSmall = fu.cropImage(bfSmall, 0, 0, 500, 500);
        }
        fu.save(bfSmall, RESOURCE_PATH.concat("/").concat(urlFolderPath).concat("/").concat(urlImage));


        if(user.getAvatar()!=null && !"".equals(user.getAvatar())){
            FileUtils.deleteFile(RESOURCE_PATH.concat("/").concat(user.getAvatar()));
        }

        user.setAvatar(urlAvatar);
        userService.update(user);



        return BackendData.success(urlAvatar);
    }


}
