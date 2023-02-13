package com.oopclass.packageApp.controllers;

import com.oopclass.packageApp.models.Package;
import com.oopclass.packageApp.repositories.PackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@Service
@SpringBootApplication
public class PackageController {

    @Autowired
    private PackageRepository packageRepository;


    public int countPage() {
        Iterable<Package> packages = packageRepository.findAll();
        int count = 1;

        for( Package p : packages) {
            count++;
        }
        float pageCount = count/10;
        if ((count%2) != 0) {
            pageCount += 1;
        }
        return (int) pageCount;
    }

    @GetMapping("/package-list/page/{offset}")
    public String getAllPackage(Model model, @PathVariable int offset){
        Iterable<Package> packages = packageRepository.findAll(PageRequest.of(offset, 10));
        int numberOfPages = countPage();
        //model.addAttribute("pages", IntStream.range(0, numberOfPages).boxed().collect(Collectors.toList()));
        model.addAttribute("currentPage", offset);
        model.addAttribute("packages", packages);

        return "package-list";
    }

//    @GetMapping("package-list/page")
//    public String getPackagePagination(Model model){
//        Iterable<Package> packages = packageRepository.findAll(PageRequest.of(offset, 10));
//        model.addAttribute("packages", packages);
//
//        return "package-list";
//    }

    @PostMapping("package/add")
    public String importNewPackage(@RequestParam String name,
                                @RequestParam String phone_number,
                                @RequestParam String address,
                                Model model){
        Package p = new Package(name, phone_number, address);
        packageRepository.save(p);
        return "redirect:/package-list/page/0";
    }

    @GetMapping("package/addPackage")
    public String importPackage(Model model){
        return "import-package";
    }

    @GetMapping("package/package-detail")
    public String getPackage(@RequestParam (name="id", required = false) Long id, Model model) {
        if(!packageRepository.existsById(id)){
            return "redirect:/";
        }
        Package p = packageRepository.findById(id).orElseThrow();
        model.addAttribute("package", p);
        return "package-detail";
    }

    @GetMapping("package/edit")
    public String editPackage(@RequestParam (name="id", required = false) Long id, Model model) {
        if(!packageRepository.existsById(id)){
            return "redirect:/package-list/page/0";
        }
        Package p = packageRepository.findById(id).orElseThrow();
        model.addAttribute("package", p);
        return "package-edit";
    }

    @PostMapping("package/submitEdit")
    public String editPackage(@ModelAttribute Package p, Model model) {
        packageRepository.save(p);
       return "redirect:package-detail?id=" + p.getId();
    }

    @GetMapping("package/delete")
    public String deletePackage(@RequestParam (name="id", required = false) Long id, Model model) {
        if(!packageRepository.existsById(id)){
            return "redirect:/package-list/page/0";
        }
        Package p = packageRepository.findById(id).orElseThrow();
        packageRepository.delete(p);
        return "redirect:/package-list/page/0";
    }


    @GetMapping("/download")
    public ResponseEntity<String> downloadCSV() {
        Iterable<Package> packages = packageRepository.findAll();

        StringBuilder csvBuilder = new StringBuilder();
        csvBuilder.append("Id,Receiver_name,Phone_number,Address\n");
        for (Package p : packages) {
            csvBuilder.append(p.getId()).append(",")
                    .append(p.getReceiver_name()).append(",")
                    .append(p.getPhone_number()).append(",")
                    .append(p.getAddress()).append("\n");
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=list.csv")
                .header(HttpHeaders.CONTENT_TYPE, "text/csv")
                .body(csvBuilder.toString());
    }



}



