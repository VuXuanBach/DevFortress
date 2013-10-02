/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import model.facade.DevModel;

/**
 *
 * @author Nguyen Ba Dao
 */
public class TestSaveLoad {

    public static void main(String[] args) {
        DevModel model = DevModel.getInstance();
//        model.save();
//        model.load();
        System.out.println(Utilities.maleList.size());
        System.out.println(model.getBalance());
        System.out.println(model.getWeek());

    }
}
