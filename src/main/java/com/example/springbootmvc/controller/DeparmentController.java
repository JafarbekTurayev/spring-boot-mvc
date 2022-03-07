package com.example.springbootmvc.controller;

import com.example.springbootmvc.dto.DepartmentDTO;
import com.example.springbootmvc.entity.Company;
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


    @GetMapping
    public String getDepartmentPage(Model model) {

        model.addAttribute("list", departmentRepository.findAll());
        //listini yuborish
        return "department/department";
    }

    @GetMapping("/add")
    public String getSavedepartment(Model model) {
        model.addAttribute("companyList", companyRepository.findAll());
        return "department/department-add";
    }

    @PostMapping("/add")
    public String saveDepartment(Model model, @ModelAttribute DepartmentDTO dto) {
        departmentService.add(dto);
        return "redirect:/department";
    }

    @GetMapping("/edit/{id}")
    public String editPage(@PathVariable(value = "id")Integer id,Model model){
        Optional<Department> byId = departmentRepository.findById(id);
        Department department = byId.get();
        model.addAttribute("department",department);
        return "department/department-edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable(value = "id")Integer id,@ModelAttribute DepartmentDTO departmentDTO){
        Optional<Department> byId = departmentRepository.findById(id);
        Department department = byId.get();
        Optional<Company> byId1 = companyRepository.findById(departmentDTO.getCompanyId());
        Company company = byId1.get();
        department.setId(id);
        department.setName(departmentDTO.getName());
        department.setCompany(company);
        departmentService.add(departmentDTO);
        return "redirect:/department";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        departmentRepository.deleteById(id);
        return "redirect:/department";
    }
}
