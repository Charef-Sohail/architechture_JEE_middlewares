package ma.enset.productsmvc.web;

import ma.enset.productsmvc.entities.Product;
import ma.enset.productsmvc.repository.ProductRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductController {
    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @GetMapping("/index")
    public String index(Model model){
        List<Product> products = productRepository.findAll();
        model.addAttribute("products",products);
        return "products";
    }

    @GetMapping("/deleteProduct")
    public String delete(@RequestParam(name="id") Long id){
        productRepository.deleteById(id);
        return "redirect:/index";
    }

}
