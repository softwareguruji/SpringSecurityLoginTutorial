package com.example.htl.W1.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.htl.W1.model.Cart;
import com.example.htl.W1.model.CartItem;
import com.example.htl.W1.model.CustomMenuItem;
import com.example.htl.W1.model.FixedMenuItems;
import com.example.htl.W1.service.CustomMenuItemService;
import com.example.htl.W1.service.FixedMenuItemService;

@Controller
public class MenuCustomerController {

	@Autowired
	FixedMenuItemService fixedMenuItemService;

	@Autowired
	CustomMenuItemService customMenuItemService;
	
	@Autowired 
	@Qualifier("cart")
	private Cart cartObject;
	
	@RequestMapping(value={"/", "/fixedMenuList"}, method = RequestMethod.GET)
	public ModelAndView fixedMenuListShow(){
		ModelAndView modelAndView = new ModelAndView();

		List<FixedMenuItems> fixedMenuItemsList = fixedMenuItemService.getByAll();
		modelAndView.addObject("fixedMenuItemsList", fixedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.FIXED_MENU_ITEM.getText());
		
		
		modelAndView.setViewName("/item_for_customer/fixed_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/customMenuList", method = RequestMethod.GET)
	public ModelAndView customMenuListShow(){
		ModelAndView modelAndView = new ModelAndView();

		List<CustomMenuItem> customizedMenuItemsList = customMenuItemService.getByAll();
		modelAndView.addObject("customMenuItemsList", customizedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.CUSTOM_MENU_ITEM.getText());
		
		modelAndView.setViewName("/item_for_customer/customize_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(@ModelAttribute("cartItemObj") CartItem cartItemObj){
		ModelAndView modelAndView = new ModelAndView();
		
		if(cartObject == null){
			cartObject = new Cart();
		}
		
		/*List<CartItem> cartItems = cartObject.getCartItems();
		
		boolean alreadyAddedSameMenuItem = false;
		for (CartItem cartItem : cartItems) {
			if(cartItem.getMenuId().equals(cartItemObj.getMenuId())){
				cartItem.setQuantity(cartItem.getQuantity()+1);
				alreadyAddedSameMenuItem = true;
			}
		}
		
		if(!alreadyAddedSameMenuItem){
			cartItemObj.setQuantity(1);
			cartObject.getCartItems().add(cartItemObj);
		}*/
		
		modelAndView.setViewName("/item_for_customer/customize_menu_list");
		return modelAndView;
	}

	public Cart getCartObject() {
		return cartObject;
	}

	public void setCartObject(Cart cartObject) {
		this.cartObject = cartObject;
	}
	
	
	
}
