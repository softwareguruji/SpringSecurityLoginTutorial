package com.example.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.model.MultiLanguageProduct;
import com.example.service.ProductService;

@Controller
public class ProductMultiLanguageController {

	@Autowired
	private ProductService productService;
	
	@RequestMapping(value="/product", method = RequestMethod.GET)
	public ModelAndView product(){
		ModelAndView modelAndView = new ModelAndView();
		MultiLanguageProduct multiLangProdObj = new MultiLanguageProduct();
		//multiLangProdObj.setManifold(true);
		modelAndView.addObject("product", multiLangProdObj);
		modelAndView.setViewName("product_add");
		return modelAndView;
	}
	
	@RequestMapping(value = "/product", method = RequestMethod.POST)
	public ModelAndView createNewProduct(@ModelAttribute("product") @Valid MultiLanguageProduct productObj, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		productObj.setActive(true);
		List<MultiLanguageProduct> productExists = productService.findProductByProductName(productObj.getProductName());
		if (productExists != null
				&& !productExists.isEmpty()) {
			bindingResult
					.rejectValue("productName", "error.product",
							"There is already a product registered with the same product name");
		}
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("product_add");
		} else {
			productService.saveProduct(productObj);
			modelAndView.addObject("successMessage", "Product has been registered successfully");
			modelAndView.addObject("product", new MultiLanguageProduct());
			modelAndView.setViewName("product_add");
			
		}
		return modelAndView;
	}
	
}