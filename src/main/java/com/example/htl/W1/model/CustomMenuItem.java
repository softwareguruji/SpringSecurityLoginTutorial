package com.example.htl.W1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="custom_menu_item")
public class CustomMenuItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name="custom_menu_item_id", columnDefinition="int(11)")
	private long customMenuItemId;
	
	@Column(name="custom_description")
	private String customizationDescription;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="ref_menu_id", nullable=false, columnDefinition="int(11)")
	private Menu menuItemReference;

	public long getCustomMenuItemId() {
		return customMenuItemId;
	}

	public void setCustomMenuItemId(long customMenuItemId) {
		this.customMenuItemId = customMenuItemId;
	}

	public String getCustomizationDescription() {
		return customizationDescription;
	}

	public void setCustomizationDescription(String customizationDescription) {
		this.customizationDescription = customizationDescription;
	}

	public Menu getMenuItemReference() {
		return menuItemReference;
	}

	public void setMenuItemReference(Menu menuItemReference) {
		this.menuItemReference = menuItemReference;
	}

	
	
	
}
