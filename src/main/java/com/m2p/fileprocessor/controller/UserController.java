package com.m2p.fileprocessor.controller;

import com.m2p.fileprocessor.UserDTO.UserDTO;
import com.m2p.fileprocessor.database.DatabaseConnection;
import com.m2p.fileprocessor.service.ReadExcel;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class UserController {

  private static String fileType =
      "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

  private static Logger log = Logger.getLogger(UserController.class.getSimpleName());

  @GetMapping(value = "/")
  public ModelAndView getHomePage() {
    ModelAndView modelAndView = new ModelAndView("home");
    DatabaseConnection connection = DatabaseConnection.getInstance();
    connection.createTable();
    return modelAndView;
  }

  @GetMapping(value = "/view")
  public ModelAndView getToViewPage() {
    ModelAndView modelAndView = new ModelAndView("view");
    DatabaseConnection.getInstance().getUsers();

    List<Object> userDTO = DatabaseConnection.getInstance().getUsers();

    if (userDTO != null && userDTO.size() > 0) modelAndView.addObject("data", userDTO);
    else modelAndView.addObject("data", "No data");

    return modelAndView;
  }

  @PostMapping(value = "/upload")
  public ModelAndView upload(@RequestParam("file") MultipartFile file) throws IOException {

    ModelAndView modelAndView = new ModelAndView("success");

    if (!fileType.equalsIgnoreCase(file.getContentType())) {
      modelAndView.addObject("message", "Sorry!, Invalid Excel File, File type should be .xlsx");
      return modelAndView;
    }

    ReadExcel readExcel = ReadExcel.getInstance();
    readExcel.readExcelUsingWorkbook(file.getInputStream());

    return modelAndView;
  }
}
