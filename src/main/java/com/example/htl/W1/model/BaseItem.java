package com.example.htl.W1.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="base_items")
public class BaseItem {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "base_item_id")
	private long baseItemId;
	
	@Column(name = "item_name")
	@NotEmpty(message = "*Please provide Item Name")
	private String itemName;
	
	@OneToOne
	@JoinColumn(name = "item_type_ref")
	private ItemType itemTypeObj;
	
	public long getBaseItemId() {
		return baseItemId;
	}

	public void setBaseItemId(long baseItemId) {
		this.baseItemId = baseItemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public ItemType getItemTypeObj() {
		return itemTypeObj;
	}

	public void setItemTypeObj(ItemType itemTypeObj) {
		this.itemTypeObj = itemTypeObj;
	}
	
}
