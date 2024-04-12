package edu.tcu.cd.hogwartsartifactsonline.system.exception;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException(String objectName, String id) {
        super("Could not find" + objectName + objectName + "with Id " + id + " :(");
    }

    public ObjectNotFoundException(String objectName, Integer id) {
        super("Could not find" + objectName + objectName + "with Id " + id + " :(");
    }

}
