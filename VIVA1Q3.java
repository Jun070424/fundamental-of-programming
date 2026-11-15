/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.Scanner;
/**
 *
 * @author User
 */
public class VIVA1Q3 {
    public static void main(String[] args) {
        Scanner sc=new Scanner(System.in);
        
        
        //declaration
        
        int parcelProcess=0;
        double totalfees = 0.0;
        int heaviest = 0;
        
        
        
        
        //read postcode
        while(true){
            String adjustment = "None";
            System.out.print("Enter destination postcode (or EXIT to stop): ");
            String postcode = sc.nextLine();
            
            //loop quit
            if(postcode.equalsIgnoreCase("EXIT")){
                break;
            }
            
        //Normalised the postcode
        postcode = postcode.replace(" ","");//delete space in postcode
        postcode = postcode.replace("-","");//delete '-' in postcode
        postcode = postcode.toUpperCase();//change all letter in postcode to upper case
        
        if(postcode.startsWith("MY")){
            postcode = postcode.substring(2);//if postcode start with MY(represent malaysia)delete the first two letter MY
        }
        
        //classifying the destination
        String zone;
        boolean international = false;
        int area = 0;
        
        //extract out the letter which represent the foreign country
        if(postcode.length()>=2
                && postcode.charAt(0)>='A' && postcode.charAt(0)<='Z'
                && postcode.charAt(1)>='A' && postcode.charAt(1)<='Z'){
            String countryCode = postcode.substring(0,2);//taking the index 0 and index 1 letter
            zone = "International (" + countryCode + ")";// International (XX)
            international = true;
        }
        //
        else{
            boolean validMalaysiaPostcode = postcode.matches("[0-9]{5}");//verify postcode which have 5 digits contain number from0-9
            
            if(validMalaysiaPostcode){
                area = Integer.parseInt(postcode.substring(0,2));
                validMalaysiaPostcode = area>=1 && area<=98;    
            }
            if(!validMalaysiaPostcode){
                System.out.println("Error: Invalid postcode. Parcel skipped.\n");
                continue;
            }
            
            if (area >= 50 && area <= 60){
                zone = "Klang Valley";
            }
            else if((area >= 61 && area <= 86)||(area >= 1 && area <= 49)){
                zone = "Peninsular";
            }
            else{
                zone = "East Malaysia";
            }
        }
        
        //Read and validate the actual weight in grams
        int weightGrams = 0;
        boolean validWeight = false;
        
        while(!validWeight){
            System.out.print("Enter parcel weight (g): ");
            String weightText = sc.nextLine();
            
            try{
                weightGrams = Integer.parseInt(weightText);
                
                if(weightGrams > 0){
                    validWeight = true;
                }
                else{
                    System.out.println("Error: Weight must be greater than 0.");
                }
            }
            catch(NumberFormatException error){
                System.out.println("Error: Weight must be an integer.");
            }
        }
        
        //Read and validate dimension
        int length = 0;
        int width = 0;
        int height = 0;
        boolean validDimension = false;
        
        while(!validDimension){
            System.out.print("Enter dimensions LxWxH (cm): ");
            String dimension = sc.nextLine();
            
            String[]parts = dimension.split("[xX]",-1);
            if(parts.length == 3){
                try{
                    length = Integer.parseInt(parts[0]);
                    width = Integer.parseInt(parts[1]);
                    height = Integer.parseInt(parts[2]);
                
                    if(length>0 && width>0 && height>0){
                        validDimension = true;
                    }//endif
                }//endtry
                catch(NumberFormatException error){
                        validDimension = false;
                }//endcatch
            }
            if(!validDimension){
                System.out.println("Error: Use the format LxWxH with positive numbers, e.g. 30x20x10.");
            }//endif
        }//endwhile
        
        
        //calculate the chargeable weight depends on zone or international
        double actualKg = weightGrams  / 1000.0;
        double volumetricKg = ((double)length*width*height) / 5000.0;
        double rawChargeableKg = Math.max(actualKg,volumetricKg);
        int chargeableKg = (int)Math.ceil(rawChargeableKg);
        double fee;
        //determine fee based on international or local area
        if(international){
            fee = chargeableKg * 35.00;
        }
        else{
            double firstKgFee, additionalKgFee;
            
            if(zone.equals("Klang Valley")){
                firstKgFee = 6.00;
                additionalKgFee = 1.50;
            }
            else if(zone.equals("Peninsular")){
                firstKgFee = 8.00;
                additionalKgFee = 2.00;
            }
            else{
                firstKgFee = 14.00;
                additionalKgFee = 4.50;
            }//endif
            fee = firstKgFee +((chargeableKg-1)* additionalKgFee );
        }//endif
        
        //determine discount or surcharge baesd on chargeable kg
        
        if(chargeableKg >= 5 && chargeableKg <= 10){
            fee = fee*0.95;
            adjustment = "5% bulk discount";
        }
        else if(chargeableKg > 10){
            fee = fee*1.15;
            adjustment = "15% heavy parcel surcharge";
        }//endif
        
        parcelProcess++;
        totalfees += fee;
        
        if(chargeableKg > heaviest){
            heaviest = chargeableKg;
        }
        
        System.out.println("\nShipping Summary");
        System.out.println("Postcode: "+postcode);
        System.out.println("Zone: "+zone);
        System.out.printf("Actual Weight: %.2f kg | Volumetric Weight: %.2f kg%n",actualKg,volumetricKg);
        System.out.println("Chargeable Weight: "+chargeableKg+" kg");
        System.out.println("Adjustment: "+adjustment);
        System.out.printf("Shipping Fee: RM %.2f%n\n",fee);
        
        }//endwhile
        System.out.println("Parcels Processed: "+parcelProcess);
        System.out.printf("Total Shipping Fees: RM %.2f%n",totalfees);
        System.out.println("Heaviest Chargeable Weight: "+heaviest+" kg");
    }
}
