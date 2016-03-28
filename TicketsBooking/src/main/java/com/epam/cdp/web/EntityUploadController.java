package com.epam.cdp.web;

import com.epam.cdp.facade.BookingFacade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.FileNotFoundException;
import java.io.IOException;

@Controller
public class EntityUploadController {

    public static final String FILE = "file";
    public static final String ENTITY = "entity";
    public static final String USER_ENTITYS = "users";
    public static final String EVENT_ENTITYS = "events";
    @Autowired
    BookingFacade facade;

    @RequestMapping(value = "/UploadEntity")
    public String uploadFileForm() {
        return "/admin/UploadEntity";
    }

    @RequestMapping(value = "/UploadEntity", method = RequestMethod.POST)
    public ModelAndView uploadFileHandler(@RequestParam(FILE) MultipartFile file,
                                          @RequestParam(ENTITY) String entity) throws
                                                                               IOException {
        checkInputFile(file);
        switch (entity) {
            case USER_ENTITYS:
                facade.loadUsersFromFile(file.getInputStream());
                break;
            case EVENT_ENTITYS:
                facade.loadEventsFromFile(file.getInputStream());
        }
        return new ModelAndView("/admin/result/FileUploadSuccess", "fileName",
                                file.getOriginalFilename());
    }

    private void checkInputFile(@RequestParam(FILE) MultipartFile file)
            throws FileNotFoundException {
        if (file == null) {
            throw new FileNotFoundException("Input file is Empty");
        }
    }

}
