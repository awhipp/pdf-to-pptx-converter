package com.whipp.client;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;

import javax.imageio.ImageIO;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.poi.sl.usermodel.PictureData.PictureType;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xslf.usermodel.XMLSlideShow;
import org.apache.poi.xslf.usermodel.XSLFPictureData;
import org.apache.poi.xslf.usermodel.XSLFPictureShape;
import org.apache.poi.xslf.usermodel.XSLFSlide;

public class Main {
	//creating empty presentation
	public static void main(String[] args) {

		String currentOutfile = "null.pptx";
		try {
			File folder = new File("input/");
			File[] listOfFiles = folder.listFiles();
			for(File file : listOfFiles){
				if(file.getName().endsWith(".pdf")){
					 try {
					        XMLSlideShow ppt = new XMLSlideShow();
					        String sourceDir = file.getAbsolutePath(); // Pdf files are read from this folder
					        String destinationDir = "temp/" + file.getName() + "/"; // converted images from pdf document are saved here

					        File sourceFile = new File(sourceDir);
					        File destinationFile = new File(destinationDir);
					        if (!destinationFile.exists()) {
					            destinationFile.mkdir();
					            System.out.println("Folder Created -> "+ destinationFile.getAbsolutePath());
					        }
					        
					        
					        if (sourceFile.exists()) {
					            System.out.println("Images copied to Folder: "+ destinationFile.getName());             
					            PDDocument document = PDDocument.load(sourceDir);
					            List<PDPage> list = document.getDocumentCatalog().getAllPages();
					            System.out.println("Total files to be converted -> "+ list.size());

					            String fileName = sourceFile.getName().replace(".pdf", "");             
					            int pageNumber = 1;
					            for (PDPage page : list) {
					                BufferedImage image = page.convertToImage();
					                File outputfile = new File(destinationDir + fileName +"_"+ pageNumber +".png");
					                ImageIO.write(image, "png", outputfile);
					                pageNumber++;
					                
					                ppt.setPageSize(new java.awt.Dimension(1280, 720));
					                XSLFSlide slide = ppt.createSlide();
							        byte[] pictureData = IOUtils.toByteArray(new FileInputStream(outputfile.getAbsolutePath()));
							        
							        XSLFPictureData pd = ppt.addPicture(pictureData, PictureType.PNG);
							        XSLFPictureShape pic = slide.createPicture(pd);
							        pic.setAnchor(new java.awt.Rectangle(0, 0, 1280, 720));
					            }
					            document.close();
					            System.out.println("Converted Images are saved at -> "+ destinationFile.getAbsolutePath());
					        } else {
					            System.err.println(sourceFile.getName() +" File not exists");
					        }

					        FileOutputStream out = new FileOutputStream("output/" + file.getName() + ".pptx");
					        ppt.write(out);
					        out.close();
					        ppt.close();
					    } catch (Exception e) {
					        e.printStackTrace();
					    }
				}
			}
		} catch (Exception e) {
			System.out.println("--FAILURE: " + currentOutfile);
			e.printStackTrace(); 
		}

		System.exit(0);
	}
}
