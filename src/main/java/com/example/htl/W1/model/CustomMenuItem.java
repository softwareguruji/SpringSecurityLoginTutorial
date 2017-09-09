package com.example.htl.W1.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
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
	
	@OneToOne(fetch = FetchType.LAZY, cascade={CascadeType.DETACH})
	@JoinColumn(name="ref_menu_id", nullable=false, columnDefinition="int(11)", foreignKey=@ForeignKey(name="fk_tbl_cmi_menu_id"), referencedColumnName="menu_id")
	//@PrimaryKeyJoinColumn(name="ref_menu_id", referencedColumnName="menu_id")
	private Menu menuItemReference;

	@OneToMany(fetch = FetchType.LAZY)
	private Set<CustomMenuItemOptions> menuItemQuestions;
	
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
