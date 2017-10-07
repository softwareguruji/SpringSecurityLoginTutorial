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
import com.example.service.UserService;

@Controller
public class MenuCustomerController {

	@Autowired
	FixedMenuItemService fixedMenuItemService;

	@Autowired
	CustomMenuItemService customMenuItemService;

	@Autowired
	MenuService menuService;
	
	@Autowired
	UserService userService;

	@Autowired
	OrderService orderService;

	@Autowired
	CartItemService cartItemService;
	
	/*@Autowired 
	@Qualifier("cart")*/
	/*private Cart cartObject;*/
	
	@RequestMapping(value={"/", "/fixedMenuList"}, method = RequestMethod.GET)
	public ModelAndView fixedMenuListShow(Principal principal){
		ModelAndView modelAndView = new ModelAndView();

		List<FixedMenuItems> fixedMenuItemsList = fixedMenuItemService.getByAll();
		modelAndView.addObject("fixedMenuItemsList", fixedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.FIXED_MENU_ITEM.getText());
		
		//Adding UserName and Cart Item
		if(principal != null){
			modelAndView.addObject("username",principal.getName());
			User userObj = userService.findUserByEmail(principal.getName());
			List<Order> listOrders = orderService.findUnOrderedCartItemsByUser(userObj);
			int cartItemsCount = 0;
			if(listOrders != null && !listOrders.isEmpty()){
				Order orderObj = listOrders.get(0);
				for (CartItem cartItem : orderObj.getCartItems()) {
					cartItemsCount += cartItem.getQuantity();
				}
			}
			modelAndView.addObject("cartItemsCount", cartItemsCount);
		}
		
		modelAndView.setViewName("/item_for_customer/fixed_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/customMenuList", method = RequestMethod.GET)
	public ModelAndView customMenuListShow(Principal principal){
		ModelAndView modelAndView = new ModelAndView();

		List<CustomMenuItem> customizedMenuItemsList = customMenuItemService.getByAll();
		modelAndView.addObject("customMenuItemsList", customizedMenuItemsList);
		modelAndView.addObject("activeHeaderMenu", HeaderLinks.CUSTOM_MENU_ITEM.getText());
		
		//Adding UserName and Cart Item
		if(principal != null){
			modelAndView.addObject("username",principal.getName());
			User userObj = userService.findUserByEmail(principal.getName());
			List<Order> listOrders = orderService.findUnOrderedCartItemsByUser(userObj);
			int cartItemsCount = 0;
			if(listOrders != null && !listOrders.isEmpty()){
				Order orderObj = listOrders.get(0);
				for (CartItem cartItem : orderObj.getCartItems()) {
					cartItemsCount += cartItem.getQuantity();
				}
			}
			modelAndView.addObject("cartItemsCount", cartItemsCount);
		}
		
		
		modelAndView.setViewName("/item_for_customer/customize_menu_list");
		return modelAndView;
	}
	
	@RequestMapping(value="/addToCart", method = RequestMethod.POST)
	public ModelAndView addToCart(Principal principal, @ModelAttribute("menuId") String menuId){
		ModelAndView modelAndView = new ModelAndView();
		
		/*if(cartObject == null){
			cartObject = new Cart();
		}*/
		
		/*System.out.println("Menu Id: "+menuId);
		List<CartItem> cartItems = cartObject.getCartItems();
		
		boolean alreadyAddedSameMenuItem = false;
		
		if(cartItems == null){
			cartItems = new ArrayList<>();
			cartObject.setCartItems(cartItems);
		}
		System.out.println("Cart Size : "+cartObject.getCartItems().size());
		
		for (CartItem cartItem : cartItems) {
			if(cartItem.getMenuId().equals(menuId)){
				cartItem.setQuantity(cartItem.getQuantity()+1);
				alreadyAddedSameMenuItem = true;
			}
		}*/
		
		/*if(!alreadyAddedSameMenuItem){
			CartItem cartItemObj = new CartItem(); 
			cartItemObj.setQuantity(1);
			cartItemObj.setMenuId(menuService.getByPK(Long.parseLong(menuId)));
			cartObject.getCartItems().add(cartItemObj);
		}*/
		
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
		
		modelAndView.addObject("username",principal.getName());
		
		RedirectView redirectView = new RedirectView("/fixedMenuList");
		//modelAndView.addObject("cartObject", cartObject);
		//modelAndView.setViewName("/item_for_customer/fixed_menu_list");
		modelAndView.setView(redirectView);
		return modelAndView;
	}

	/*public Cart getCartObject() {
		return cartObject;
	}

	public void setCartObject(Cart cartObject) {
		this.cartObject = cartObject;
	}*/
	
	
	
}
