/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inicio;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JTable;
import javax.swing.filechooser.FileNameExtensionFilter;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


/**
 *
 * @author rodri
 */
   public class Exportar{
        
     
        public void esportarexcel(JTable t) throws IOException{
            
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivo de Excel","xls");
            chooser.setFileFilter(filter);
            chooser.setDialogTitle("Guardar Archivo");
            chooser.setAcceptAllFileFilterUsed(false); 
            
            if(chooser.showSaveDialog(null)== JFileChooser.APPROVE_OPTION){
                String ruta = chooser.getSelectedFile().toString().concat(".xls");
                try{
                    File archivoXLS =new File(ruta);
                    if(archivoXLS.exists()){
                        archivoXLS.delete();
                    }
                    archivoXLS.createNewFile();
                    Workbook libro = new HSSFWorkbook();
                    FileOutputStream archivo =new FileOutputStream(archivoXLS);
                    Sheet hoja = libro.createSheet("Datos");
                    hoja.setDisplayGridlines(false);
                    
                    for(int f = 0; f< t.getRowCount(); f++){
                        Row fila= hoja.createRow(f);
                        for(int c = 0; c< t.getColumnCount(); c++){
                            Cell celda = fila.createCell(c);
                            if(f==0){
                                celda.setCellValue(t.getColumnName(c));
                            }//fin if
                        }//fin for 2
                    }//fin form 1
                    
                    
                    int filaInicio =1;
                    for(int f=0; f < t.getRowCount(); f++){
                        Row fila =hoja.createRow(filaInicio);
                        for(int c= 0; c< t.getColumnCount(); c++){
                            Cell celda = fila.createCell(c);
                            if (t.getValueAt(f,c) instanceof Double){
                                celda.setCellValue(Double.parseDouble(t.getValueAt(f,c).toString()));
                                
                            }else if(t.getValueAt(f,c) instanceof Float){
                                celda.setCellValue(Float.parseFloat((String)t.getValueAt(f,c)));
                            }
                        }
                        
                    }
                    libro.write(archivo);
                    archivo.close();
                    Desktop.getDesktop().open(archivoXLS);
                    
                }catch(IOException | NumberFormatException e){
                    throw e;
                }
            } //fin if

            }
            //fin if
        }

   
   
 