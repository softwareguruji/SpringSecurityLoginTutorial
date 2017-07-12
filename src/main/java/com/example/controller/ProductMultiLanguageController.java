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
	
	@RequestMapping(value="/admin/product", method = RequestMethod.GET)
	public ModelAndView product(){
		ModelAndView modelAndView = new ModelAndView();
		MultiLanguageProduct multiLangProdObj = new MultiLanguageProduct();
		//multiLangProdObj.setManifold(true);
		modelAndView.addObject("product", multiLangProdObj);
		modelAndView.setViewName("/admin/product_add");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/product", method = RequestMethod.POST)
	public ModelAndView createNewProduct(@ModelAttribute("product") @Valid MultiLanguageProduct productObj, BindingResult bindingResult) {
		ModelAndView modelAndView = new ModelAndView();
		productObj.setActive(true);
		
		long productId = productObj.getProductId();
		boolean isUpdateCall = false;
		if(productId != 0){
			isUpdateCall = true;
		}
		
		System.out.println("Is Manifold ? "+productObj.isManifold());
		
		List<MultiLanguageProduct> productExists = productService.findProductByProductName(productObj.getProductName());
		if (productExists != null
				&& !productExists.isEmpty()) {
			
			if(isUpdateCall){
				if(productExists.size()>0){
					if(productExists.get(0).getProductId() != productObj.getProductId()){
						bindingResult
							.rejectValue("productName", "error.product",
									"There is already a product registered with the same product name");
					}
				}
			}else{
				bindingResult
					.rejectValue("productName", "error.product",
							"There is already a product registered with the same product name");
			}
		}
		
		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/admin/product_add");
		} else {
			productService.saveProduct(productObj);
			modelAndView.addObject("product", new MultiLanguageProduct());
			if(isUpdateCall){
				modelAndView.addObject("successMessage", "Product has been updated successfully");
			}else{
				modelAndView.addObject("successMessage", "Product has been saved successfully");
			}
			modelAndView.setViewName("/admin/product_add");				
		}
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/viewUpdateProduct", method = RequestMethod.GET)
	public ModelAndView viewUpdateProduct(@ModelAttribute("product_id") String productId){
		ModelAndView modelAndView = new ModelAndView();
		MultiLanguageProduct multiLangProdObj = productService.getProductById(Long.parseLong(productId));
		modelAndView.addObject("product", multiLangProdObj);
		modelAndView.setViewName("/admin/product_add");
		return modelAndView;
	}
	
	@RequestMapping(value = "/admin/deleteProduct", method = RequestMethod.GET)
	public ModelAndView deleteProduct(@ModelAttribute("product_id") String productId){
		ModelAndView modelAndView = new ModelAndView();
		productService.deleteProduct(Long.parseLong(productId));
		modelAndView.setViewName("/admin/productlist_rest");
		return modelAndView;
	}
	
	
	@RequestMapping(value = "/admin/listProduct", method = RequestMethod.GET)
	public ModelAndView listProducts(){
		ModelAndView modelAndView = new ModelAndView();
		modelAndView.setViewName("/admin/productlist_rest");
		return modelAndView;
	}
	
}
