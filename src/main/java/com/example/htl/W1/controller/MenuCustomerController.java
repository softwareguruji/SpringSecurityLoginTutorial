package com.example.htl.W1.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.example.htl.W1.model.CartItem;
import com.example.htl.W1.model.CustomMenuItem;
import com.example.htl.W1.model.FixedMenuItems;
import com.example.htl.W1.model.Menu;
import com.example.htl.W1.model.Order;
import com.example.htl.W1.service.CartItemService;
import com.example.htl.W1.service.CustomMenuItemService;
import com.example.htl.W1.service.FixedMenuItemService;
import com.example.htl.W1.service.MenuService;
import com.example.htl.W1.service.OrderService;
import com.example.model.User;

@Controller
public class MenuCustomerController extends BaseController{

	@Autowired
	FixedMenuItemService fixedMenuItemService;

	@Autowired
	CustomMenuItemService customMenuItemService;

	@Autowired
	MenuService menuService;
	
	/*@Autowired
	UserService userService;*/

	/*@Autowired
	OrderService orderService;*/

	@Autowired
	CartItemService cartItemService;
	
	@RequestMapping(value={"/", "/fixedMenuList"}, method = RequestMethod.GET)
	public ModelAndView fixedMenuListShow(Principal principal){
		ModelAndView modelAndView = new ModelAndView();

		setupBaseParameter(modelAndView, principal); 
		
		List<FixedMenuItems> fixedMenuItemsList = fixedMenuItemService.getByAll();
		modelAndView.addObject("fixedMenuItemsList", fixedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.FIXED_MENU_ITEM.getText());
		
		modelAndView.setViewName("/item_for_customer/fixed_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/customMenuList", method = RequestMethod.GET)
	public ModelAndView customMenuListShow(Principal principal){
		ModelAndView modelAndView = new ModelAndView();

		setupBaseParameter(modelAndView, principal); 
		
		List<CustomMenuItem> customizedMenuItemsList = customMenuItemService.getByAll();
		modelAndView.addObject("customMenuItemsList", customizedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.CUSTOM_MENU_ITEM.getText());
		
		modelAndView.setViewName("/item_for_customer/customize_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(Principal principal, @ModelAttribute("menuId") String menuId){
		ModelAndView modelAndView = new ModelAndView();
		
		User userObj = userService.findUserByEmail(principal.getName());
		
		List<Order> orders = orderService.findUnOrderedCartItemsByUser(userObj);
		if(orders != null && !orders.isEmpty()){
			List<CartItem> listCartItem = orders.get(0).getCartItems();
			boolean sameItemFoundInCart = false;
			
			CartItem sameCartItemObj = null;
			
			if(!listCartItem.isEmpty()){
				for (CartItem cartItem : listCartItem) {
					if(cartItem.getMenuId().getMenuId() == Long.parseLong(menuId)){
						sameItemFoundInCart = true;
						sameCartItemObj = cartItem;
					}
				}
			}

			if(sameItemFoundInCart){
				sameCartItemObj.setQuantity(sameCartItemObj.getQuantity()+1);
				cartItemService.save(sameCartItemObj);
			}else{
				
				Menu menuObj = menuService.getByPK(Long.parseLong(menuId));
				
				CartItem item = new CartItem();
				item.setOrder(orders.get(0));
				item.setQuantity(1);
				item.setMenuId(menuObj);
				item.setPrice(menuObj.getFixedMenuItemObj().getPrice());
				cartItemService.save(item);
			}
		}else{
			Order order = new Order();
			order.setUser(userObj);
			order.setOrderDate(new Date());
			
			//Setting up cart Item
			Menu menuObj = menuService.getByPK(Long.parseLong(menuId));
			CartItem item = new CartItem();
			item.setOrder(order);
			item.setQuantity(1);
			item.setMenuId(menuObj);
			item.setPrice(menuObj.getFixedMenuItemObj().getPrice());
			
			List<CartItem> listCartItems = new ArrayList<>();
			listCartItems.add(item);
			order.setCartItems(listCartItems);
			
			orderService.saveNewOrder(order);
		}
		
		//modelAndView.addObject("username",principal.getName());
		
		RedirectView redirectView = new RedirectView("/fixedMenuList");
		modelAndView.setView(redirectView);
		return modelAndView;
	}

	
	
	
}
