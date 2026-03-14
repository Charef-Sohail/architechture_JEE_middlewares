package ma.enset.productsmvc.web;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import ma.enset.productsmvc.entities.Product;
import ma.enset.productsmvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @GetMapping("/user/index")
    public String listProducts(@RequestParam(name = "keyword", required = false) String keyword, Model model){
        List<Product> products;
        System.out.println(keyword);
        if(keyword!=null && !keyword.isEmpty()){
            System.out.println(keyword);
            products = productRepository.findByNameContainingIgnoreCase(keyword);
        } else{
            products = productRepository.findAll();
        }
        model.addAttribute("products",products);
        model.addAttribute("keyword",keyword);
        //        List<Product> products = productRepository.findAll();
//        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }

    @PostMapping("/admin/deleteProduct")
    public String delete(@RequestParam(name="id") Long id){
        productRepository.deleteById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/newProduct")
    public String Add(Model model){
        model.addAttribute("product", new Product());
        return "new-product";
    }

    @PostMapping("/admin/saveProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "new-product";
        }
        productRepository.save(product);
        return "redirect:/user/index";
    }

    @PostMapping("/admin/saveEditedProduct")
    public String saveProduct(@Valid Product product, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return "edit-product";
        }

        productRepository.save(product);

        return "redirect:/user/index";
    }

    @GetMapping("/notAuthorized")
    public String notAuthorized(){
        return "errors/403";
    }


    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:login";
    }


    @GetMapping("/admin/editProduct")
    public String edit(@RequestParam(name="id") Long id,Model model){
        model.addAttribute("product",productRepository.findById(id).orElse(null));
        return "edit-product";
    }

}
