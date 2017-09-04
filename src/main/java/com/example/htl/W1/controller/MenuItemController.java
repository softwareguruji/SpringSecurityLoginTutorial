package com.example.htl.W1.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.example.htl.W1.model.BaseItem;
import com.example.htl.W1.model.ItemType;
import com.example.htl.W1.model.Menu;
import com.example.htl.W1.model.MenuType;
import com.example.htl.W1.service.BaseItemService;
import com.example.htl.W1.service.FixedMenuItemService;
import com.example.htl.W1.service.ItemTypeService;
import com.example.htl.W1.service.MenuService;
import com.example.htl.W1.service.MenuTypeService;

@Controller
public class MenuItemController {

	@Autowired
	ItemTypeService itemTypeService;

	@Autowired
	BaseItemService baseItemService;

	@Autowired
	MenuService menuService;

	@Autowired
	FixedMenuItemService fixedMenuItemService;
	
	@Autowired
	MenuTypeService menuTypeService;
	
	@RequestMapping(value="/admin/baseItem", method = RequestMethod.GET)
	public ModelAndView baseItem(@ModelAttribute("itemTypeSelected") String itemTypeSelected){
		ModelAndView modelAndView = new ModelAndView();

		System.out.println("itemTypeId: "+itemTypeSelected);
		if(itemTypeSelected != null
				&& itemTypeSelected.trim().length()>0
				&& !itemTypeSelected.trim().equals("-1")){
			
			ItemType itemTypeObj = itemTypeService.getById(Long.parseLong(itemTypeSelected));
			List<BaseItem> baseItemList = baseItemService.getByAll(itemTypeObj);
			modelAndView.addObject("baseItemList", baseItemList);
			modelAndView.addObject("itemTypeSelected",itemTypeSelected);
		}else{
			List<BaseItem> baseItemList = baseItemService.getByAll();
			modelAndView.addObject("baseItemList", baseItemList);
			modelAndView.addObject("itemTypeSelected","");
		}
		
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		BaseItem baseItemObj = new BaseItem();
		modelAndView.addObject("baseItem", baseItemObj);
		
		modelAndView.setViewName("/admin/item/base_item");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/baseItemAdd", method = RequestMethod.POST)
	public ModelAndView baseItemAdd(@ModelAttribute("baseItem") @Validated BaseItem baseItemObj, BindingResult resultBunding){
		ModelAndView modelAndView = new ModelAndView();


		//Validation with the for the same item already available or not.
		if(baseItemService.getByItemNameAndItemType(baseItemObj) != null){
			resultBunding.rejectValue("itemName", 
						"error.baseItem", 
						"Item name must be unique with same item type. \""+baseItemObj.getItemName()+" is already available with item type "+baseItemObj.getItemTypeObj().getItemTypeName()+"\"");

			List<BaseItem> baseItemList = baseItemService.getByAll(baseItemObj.getItemTypeObj());
			modelAndView.addObject("baseItemList", baseItemList);
			
		}else{
			baseItemService.saveBaseItem(baseItemObj);
			modelAndView.addObject("itemTypeSelected", baseItemObj.getItemTypeObj().getItemTypeId());

			List<BaseItem> baseItemList = baseItemService.getByAll(baseItemObj.getItemTypeObj());
			modelAndView.addObject("baseItemList", baseItemList);
			
			baseItemObj = new BaseItem();
			modelAndView.addObject("baseItem", baseItemObj);
		}
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		
		modelAndView.setViewName("/admin/item/base_item");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/baseItemEdit", method = RequestMethod.GET)
	public ModelAndView baseItemEdit(@ModelAttribute("baseItemId") String baseItemId){
		ModelAndView modelAndView = new ModelAndView();
		
		List<BaseItem> baseItemList = baseItemService.getByAll();
		modelAndView.addObject("baseItemList", baseItemList);
		 
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		BaseItem baseItemObj = baseItemService.getById(Long.parseLong(baseItemId));
		modelAndView.addObject("baseItem", baseItemObj);
		
		modelAndView.setViewName("/admin/item/base_item");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/baseItemDelete", method = RequestMethod.GET)
	public ModelAndView baseItemDelete(@ModelAttribute("baseItemId") String baseItemId){
		ModelAndView modelAndView = new ModelAndView();
		
		List<BaseItem> baseItemList = baseItemService.getByAll();
		modelAndView.addObject("baseItemList", baseItemList);
		 
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		BaseItem baseItemObj = baseItemService.getById(Long.parseLong(baseItemId));
		baseItemService.deleteBaseItem(baseItemObj);
		
		baseItemObj = new BaseItem();
		modelAndView.addObject("baseItem", baseItemObj);
		
		modelAndView.setViewName("/admin/item/base_item");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/itemType", method = RequestMethod.GET)
	public ModelAndView itemType(){
		ModelAndView modelAndView = new ModelAndView();
		
		ItemType itemTypeObj = new ItemType();
		modelAndView.addObject("itemType", itemTypeObj);
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		modelAndView.setViewName("/admin/item/item_type");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/itemType", method = RequestMethod.POST)
	public ModelAndView itemTypeAdd(@ModelAttribute("itemType") @Valid ItemType itemTypeObj, BindingResult bindingResult){
		ModelAndView modelAndView = new ModelAndView();
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		//validation for unique item add
		if(itemTypeService.getByItemType(itemTypeObj.getItemTypeName()) != null){
			bindingResult
			.rejectValue("itemTypeName", "error.itemType",
					"Item Type name must be unique. \""+itemTypeObj.getItemTypeName()+" is already added.\"");
		}else{
			itemTypeService.saveItemType(itemTypeObj);
			itemTypeObj = new ItemType();
			modelAndView.addObject("itemType", itemTypeObj);
		}
		
		modelAndView.setViewName("/admin/item/item_type");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/itemTypeDelete", method = RequestMethod.GET)
	public ModelAndView itemTypeDelete(@ModelAttribute("itemTypeId") String itemTypeId){
		ModelAndView modelAndView = new ModelAndView();

		ItemType itemTypeObj = itemTypeService.getById(Long.parseLong(itemTypeId));
		
		itemTypeService.deleteItemType(itemTypeObj);
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		ItemType itemTypeObj1 = new ItemType();
		modelAndView.addObject("itemType", itemTypeObj1);
		
		modelAndView.setViewName("/admin/item/item_type");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/itemTypeEdit", method = RequestMethod.GET)
	public ModelAndView itemTypeEditView(@ModelAttribute("itemTypeId") String itemTypeId){
		ModelAndView modelAndView = new ModelAndView();

		ItemType itemTypeObj = itemTypeService.getById(Long.parseLong(itemTypeId));
		modelAndView.addObject("itemType", itemTypeObj);
		
		List<ItemType> itemTypeList = itemTypeService.getByAll();
		modelAndView.addObject("itemTypeList", itemTypeList);
		
		modelAndView.setViewName("/admin/item/item_type");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/menuCreate", method= RequestMethod.GET)
	public ModelAndView generateMenu(){
		ModelAndView modelAndView = new ModelAndView();

		List<MenuType> menuTypeList = menuTypeService.getByAll();
		modelAndView.addObject("menuTypeList", menuTypeList);
		
		Menu menuObj = new Menu();
		modelAndView.addObject("menuGenerate", menuObj);
		
		modelAndView.setViewName("/admin/item/menu_generator");
		return modelAndView;
	}
	
	@RequestMapping(value="/admin/menuCreate", method= RequestMethod.POST)
	public ModelAndView generateMenuAddUpdate(@ModelAttribute("menuGenerate") Menu menuObj){
		ModelAndView modelAndView = new ModelAndView();

		List<MenuType> menuTypeList = menuTypeService.getByAll();
		modelAndView.addObject("menuTypeList", menuTypeList);

		if(menuObj.getFixedMenuItemObj() != null
				&& menuObj.getFixedMenuItemObj().getFixedMenuDescription() != null){
			
			System.out.println("Description: "+menuObj.getFixedMenuItemObj().getFixedMenuDescription());
			menuObj.getFixedMenuItemObj().setMenuItemReference(menuObj);
			//menuObj.setFixedMenuItemObj(fixedMenuItemService.save(menuObj.getFixedMenuItemObj()));
		}
		
		menuObj = menuService.save(menuObj);

		modelAndView.addObject("menuGenerate", menuObj);
		
		modelAndView.setViewName("/admin/item/menu_generator");
		return modelAndView;
	}
}