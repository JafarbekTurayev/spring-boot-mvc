package com.example.springbootmvc.controller;

import com.example.springbootmvc.dto.ApiResponse;
import com.example.springbootmvc.dto.DepartmentDTO;
import com.example.springbootmvc.entity.Department;
import com.example.springbootmvc.repository.CompanyRepository;
import com.example.springbootmvc.repository.DepartmentRepository;
import com.example.springbootmvc.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@Controller
@RequestMapping("/department")
public class DeparmentController {


    @Autowired
    DepartmentService departmentService;

    @Autowired
    DepartmentRepository departmentRepository;

    @Autowired
    CompanyRepository companyRepository;


    //zaproslarni tutib ishlatish
    @RequestMapping(method = RequestMethod.GET)
//    @GetMapping
    public String getDepartmentPage(Model model) {

        model.addAttribute("list", departmentRepository.findAll());
        //listini yuborish
        return "department/department";
    }

    @GetMapping("/add")
//    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String getSavedepartment(Model model) {

        model.addAttribute("companyList", companyRepository.findAll());

        return "department/department-add";
    }

    @PostMapping("/add")
    public String saveDepartment(Model model, @ModelAttribute DepartmentDTO dto) {
        departmentService.add(dto);
        return "redirect:/department";
    }

    @GetMapping("/delete/{id}") //1 45 24 90
    public String delete(@PathVariable Integer id) {
        departmentRepository.deleteById(id);
        return "redirect:/department";
    }


    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable Integer id, Model model) {

        Optional<Department> optionalDepartment = departmentRepository.findById(id);
        if (optionalDepartment.isEmpty()) return "Xatolik!";

//        departmentRepository.getById()
        model.addAttribute("edited", optionalDepartment.get());
        model.addAttribute("companyList", companyRepository.findAll());
        return "department/department-edit";
    }

    @PostMapping("/edit/{id}")
    public String editDepartment(@PathVariable Integer id, @ModelAttribute DepartmentDTO departmentDTO) {
        ApiResponse response = departmentService.edit(id, departmentDTO);
        System.out.println(response);
        return "redirect:/department";
    }


}
