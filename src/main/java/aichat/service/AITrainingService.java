/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package aichat.service;


import aichat.dao.AITrainingDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AITrainingService {

    private final AITrainingDAO trainingDAO;

    public AITrainingService() {
        this.trainingDAO = new AITrainingDAO();
    }

    public List<String> getAllProductNames() {
        try {
            return trainingDAO.getAllProductNames();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllBrandNames() {
        try {
            return trainingDAO.getAllBrandNames();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllCategoryNames() {
        try {
            return trainingDAO.getAllCategoryNames();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllColors() {
        try {
            return trainingDAO.getAllColors();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllRamValues() {
        try {
            return trainingDAO.getAllRamValues();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllRomValues() {
        try {
            return trainingDAO.getAllRomValues();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllPrices() {
        try {
            return trainingDAO.getAllPrices();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllSpecifications() {
        try {
            return trainingDAO.getAllSpecifications();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<String> getAllDescriptions() {
        try {
            return trainingDAO.getAllDescriptions();
        } catch (SQLException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    // Gộp hết lại nếu bạn cần dùng để feed vô AI hay training
    public List<String> getAllTrainingText() {
        List<String> combined = new ArrayList<>();
        combined.addAll(getAllProductNames());
        combined.addAll(getAllBrandNames());
        combined.addAll(getAllCategoryNames());
        combined.addAll(getAllColors());
        combined.addAll(getAllRamValues());
        combined.addAll(getAllRomValues());
        combined.addAll(getAllPrices());
        combined.addAll(getAllSpecifications());
        combined.addAll(getAllDescriptions());
        return combined;
    }
    public static void main(String[] args) {
        AITrainingService aITrainingService = new AITrainingService();
       List<String>Name = aITrainingService.getAllProductNames();
        for (String name : Name) {
            System.out.println(name.toString());
        }
        
    }
}
