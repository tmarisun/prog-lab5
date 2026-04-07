package org.example;

import lombok.Getter;
import org.example.commands.Command;
import org.example.exceptions.NoRightsException;
import org.example.data.City;
import org.example.manager.ManagerCommands;
import org.example.service.JsonFileInputReader;
import org.example.service.JsonFileLoader;
import org.example.validate.CityValidator;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import static org.example.validate.InputValidator.validateUniqueIds;


public class Application {

    @Getter
    private static Stack<City> cityStack;
    @Getter
    private String fileName;
    @Getter
    private ManagerCommands managerCommands;


    public Application(String filename) throws IOException, NoRightsException {
        this.fileName = filename;
        cityStack = JsonFileLoader.loadCollection(filename);

        for (City city : cityStack) {
            CityValidator.validateCity(city);
        }

        validateUniqueIds(cityStack);
        //mergeScriptCityFileIfPresent();
        this.managerCommands = new ManagerCommands(this);
    }


    /*private void mergeScriptCityFileIfPresent() {
        File scriptFile = new File(SCRIPT_CITY_FILE);
        if (!scriptFile.exists() || !scriptFile.isFile()) {
            return;
        }
        try {
            List<City> extra = JsonFileInputReader.loadAllCitiesFromJsonFile(SCRIPT_CITY_FILE);
            for (City city : extra) {
                CityValidator.validateCity(city);
                boolean duplicate = false;
                for (City existing : cityStack) {
                    if (existing.getId().equals(city.getId())) {
                        duplicate = true;
                        break;
                    }
                }
                if (duplicate) {
                    System.out.println("Warning: " + SCRIPT_CITY_FILE + " — id " + city.getId()
                            + " уже есть в коллекции, элемент пропущен.");
                    continue;
                }
                cityStack.push(city);
            }
            validateUniqueIds(cityStack);
            System.out.println("Loaded " + extra.size() + " city/cities from " + SCRIPT_CITY_FILE);
        } catch (Exception e) {
            System.out.println("Warning: не удалось загрузить " + SCRIPT_CITY_FILE + ": " + e.getMessage());
        }
    }*/

    public static long getSize() {
        return cityStack.size();
    }


    public static long getNextId() {
        long maxId = 0;
        for (City city : cityStack) {
            if (city.getId() > maxId) {
                maxId = city.getId();
            }
        }
        return maxId + 1;
    }

    public void addCity(City city) {
        cityStack.push(city);
    }

    public void help(){
        System.out.println("The list of commands available to you: ");
        Map<String, Command> commands = managerCommands.getCommands();
        for (Map.Entry<String, Command> entry : commands.entrySet()) {
            Command value = entry.getValue();
            System.out.print(value.getName() + " ------- ");
            System.out.println(value.getDescription());
        }
    }



}