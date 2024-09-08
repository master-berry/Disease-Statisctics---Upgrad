package com.upgrad.patterns.Service;

import com.upgrad.patterns.Interfaces.IndianDiseaseStat;
import com.upgrad.patterns.Constants.SourceType;
import com.upgrad.patterns.Strategies.DiseaseShStrategy;
import com.upgrad.patterns.Strategies.JohnHopkinsStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IndiaDiseaseStatFactory {
    private IndianDiseaseStat diseaseShStrategy;
    private IndianDiseaseStat johnHopkinsStrategy;

    @Autowired
    public IndiaDiseaseStatFactory(DiseaseShStrategy diseaseShStrategy, JohnHopkinsStrategy johnHopkinsStrategy)
    {
        this.diseaseShStrategy = diseaseShStrategy;
        this.johnHopkinsStrategy = johnHopkinsStrategy;
    }


    //create a method named GetInstance with return type as IndianDiseaseStat and parameter of type sourceType
    public IndianDiseaseStat GetInstance(SourceType sourceType){
        if(sourceType.equals(SourceType.DiseaseSh)){
            return diseaseShStrategy;
        }
        else if(sourceType.equals(SourceType.JohnHopkins)){
            return johnHopkinsStrategy;
        }
        else {
            throw new IllegalArgumentException("This is an invalid disease strategy. Please enter a valid strategy");
        }
    }
    //create a conditional statement
    //if the sourceType is JohnHopkins
    //return johnHopkinsStrategy
    //if the sourceType is DiseaseSh
    //return diseaseShStrategy

    //create a message for invalid disease strategy/sourceType
    //throw the message as an Illegal argument exception


}
