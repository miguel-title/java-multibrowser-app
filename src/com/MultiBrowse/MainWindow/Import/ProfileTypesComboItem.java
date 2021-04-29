/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.MultiBrowse.MainWindow.Import;

/**
 *
 * @author title
 */
public class ProfileTypesComboItem {
    private int id = 0;
    private String description = "";

    public ProfileTypesComboItem(int id, String description) {
      this.id = id;
      this.description = description;
    }

    public int getId() {
      return id;
    }

    public String getDescription() {
      return description;
    }

    @Override
    public String toString() {
      return description;
    }
}
