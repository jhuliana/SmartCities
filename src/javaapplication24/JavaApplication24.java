package javaapplication24;

import static com.sun.xml.internal.ws.spi.db.BindingContextFactory.LOGGER;
import java.io.BufferedReader;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.RDFNode;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.apache.jena.rdf.model.RDFWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import org.apache.jena.ontology.OntClass;
import org.apache.jena.util.FileManager;
import org.apache.jena.ontology.OntModel;
import org.apache.jena.ontology.OntModelSpec;
import org.apache.jena.ontology.OntProperty;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.riot.Lang;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.riot.RDFFormat;
import org.apache.jena.shared.JenaException;
import org.apache.jena.util.iterator.ExtendedIterator;
import static org.apache.jena.vocabulary.DCAT.Dataset;

public class JavaApplication24 {

    public static final String SEPARATOR = ";";
    public static final ArrayList<CentroSalud> centros = new ArrayList<CentroSalud>();

    public static void main(String[] args) throws FileNotFoundException, IOException {
        OntModel ontoModel = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM);//OWL_MEM, null o OWL_MEM_RULE_INF
        try {
            java.io.InputStream in = FileManager.get().open("CentroSalud.owl");
            if (in == null) {
                throw new IllegalArgumentException("Archivo no encontrado");
            }
            try {
                ontoModel.read(in, "");
            } catch (Exception e) {
            }
            LOGGER.log(Level.INFO, "Ontology {0} loaded.", "CentroSalud.owl");
        } catch (JenaException je) {
            System.err.println("ERROR" + je.getMessage());
            System.exit(0);
        }
        
        File f = new File("centros.rdf"); //Fijar ruta donde se creará el archivo RDF
        FileOutputStream os = new FileOutputStream(f);

        Model dboModel = ModelFactory.createDefaultModel();
        
        String dbo = "http://dbpedia.org/ontology/";
        dboModel.setNsPrefix("Dbo", dbo);

        String dct = "http://purl.org/dc/terms";
        dboModel.setNsPrefix("Dct", dct);

        String rdfs = "http://www.w3.org/2000/01/rdf-schema#";
        dboModel.setNsPrefix("Rdfs", rdfs);

        String dataPrefix = "http://centrosalud.org/data/";
        dboModel.setNsPrefix("Data", dataPrefix);

        String ontoPrefix = "http://centrosalud.org/ontology#";
        dboModel.setNsPrefix("Onto", dataPrefix);

        //lectura del archivo de especialidades
        ArrayList<String> ArchEspecialidades = new ArrayList<String>();
        BufferedReader br2 = null;
        br2 = new BufferedReader(new FileReader("especialidades.csv"));
        String line2 = br2.readLine(); 
        while (null != line2) {
            String[] fields2 = line2.split(";");
            line2 = br2.readLine();
            for (int i = 0; i < fields2.length; i += 2) {
                ArchEspecialidades.add(fields2[i]);
            }
        }

        //lectura del árchivo de códigos
        ArrayList<String> bloque = new ArrayList<String>();
        BufferedReader br1 = null;
        br1 = new BufferedReader(new FileReader("codigo.csv"));
        String line1 = br1.readLine();
        while (null != line1) {
            String[] fields1 = line1.split(";");
            line1 = br1.readLine();
            for (int i = 0; i < fields1.length; i += 2) {
                bloque.add(fields1[i]);
                bloque.add(fields1[i + 1]);
            }
        }
        //lectura del archivo de datos de centros
        BufferedReader br = null;
        br = new BufferedReader(new FileReader("pruebaUnion.csv"));
        String line = br.readLine();
        while (null != line) {
            CentroSalud centro = new CentroSalud();
            String[] fields = line.split(";");
            line = br.readLine();
            //System.out.println(line);
            ArrayList<String> especialidad = new ArrayList<String>();
            ArrayList<String> descBloque = new ArrayList<String>();
            int h, k, l;
            for (int i = 0; i < fields.length; i += 50) {
                centro.setAnio(fields[i]);
                centro.setNombCentro(fields[i + 1]);
                centro.setId(fields[i + 2]);
                centro.setProvincia(fields[i + 3]);
                centro.setCanton(fields[i + 4]);
                centro.setParroquia(fields[i + 5]);
                centro.setPais(fields[i + 6]);
                centro.setCategoria(fields[i + 7]);
                centro.setTipo(fields[i + 8]);
                centro.setDepartamento(fields[i + 9]);
                centro.setSector(fields[i + 10]);
                h = 12;
                for (int j = 0; j < 16; j++) {
                    especialidad.add(ArchEspecialidades.get(j));
                    especialidad.add(fields[i + h]);
                    h++;
                }
                centro.setCantEspecialistas(especialidad);
                centro.setFuente1(fields[i + 28]);
                centro.setResiduos(fields[i + 29]);
                k = 30;
                l = 0;
                for (int j = 0; j < 18; j++) {
                    descBloque.add(bloque.get(l));
                    descBloque.add(bloque.get(l + 1));
                    descBloque.add(fields[i + k]);
                    k++;
                    l += 2;
                }
                centro.setCantBloque(descBloque);
                centro.setbloqueNombre("Bloque_16");
                centro.setArea(fields[i + 48]);
                centro.setFuente2(fields[i + 49]);
            }
            centros.add(centro);
        }
        if (null != br) {
            br.close();
        }

        // presentar datos por consola
//        for (int i = 0; i < centros.size(); i++) {
//            System.out.println(centros.get(i).getAnio());
//            System.out.println(centros.get(i).getNombCentro());
//            System.out.println(centros.get(i).getId());
//            System.out.println(centros.get(i).getProvincia());
//            System.out.println(centros.get(i).getCanton());
//            System.out.println(centros.get(i).getParroquia());
//            System.out.println(centros.get(i).getPais());
//            System.out.println(centros.get(i).getCategoria());
//            System.out.println(centros.get(i).getTipo());
//            System.out.println(centros.get(i).getDepartamento());
//            System.out.println(centros.get(i).getSector());
//            System.out.println("ESPECIALIDADES");
//            for (int j = 0; j < centros.get(i).getCantEspecialistas().size(); j += 2) {
//                System.out.println("\t" + centros.get(i).getCantEspecialistas().get(j));
//                System.out.println("\t" + centros.get(i).getCantEspecialistas().get(j + 1));
//            }
//            System.out.println("***************");
//            System.out.println(centros.get(i).getFuente1());
//            System.out.println("*********residuos*********");
//            System.out.println(centros.get(i).getResiduos());
//            System.out.println(centros.get(i).getbloqueNombre());
//            for (int j = 0; j < centros.get(i).getCantBloque().size(); j += 3) {
//                System.out.println(centros.get(i).getCantBloque().get(j));
//                System.out.println(centros.get(i).getCantBloque().get(j + 1));
//                System.out.println(centros.get(i).getCantBloque().get(j + 2));
//                System.out.println("********");
//            }
//            System.out.println(centros.get(i).getFuente2());
//        }

        ///***********Presentar clases y propiedades*************
        ArrayList<OntClass> clas = new ArrayList<>();
        ArrayList<OntProperty> prop = new ArrayList<>();
        ExtendedIterator iteratorClasses = ontoModel.listClasses();
        while (iteratorClasses.hasNext()) {
            OntClass ontClass = (OntClass) iteratorClasses.next();
            clas.add(ontClass);
            ExtendedIterator iteratorPropiedades = ontClass.listDeclaredProperties();
            while (iteratorPropiedades.hasNext()) {
                OntProperty ontProperty1 = (OntProperty) iteratorPropiedades.next();
                prop.add(ontProperty1);
//                System.out.println("\t" + ontProperty1);
            }
        }

        //PROPIEDADES        
        OntProperty proIsPartOf = prop.get(0);
        OntProperty proArea = prop.get(1);
        OntProperty proCantBlock = prop.get(2);
        OntProperty proDetail = prop.get(3);
        OntProperty proCode = prop.get(4);
        OntProperty proCantSpecialty = prop.get(5);
        OntProperty proRecordDataWaste = prop.get(6);
        OntProperty proParish = prop.get(7);
        OntProperty proId = prop.get(8);
        OntProperty proSubject = prop.get(9);
        OntProperty proRecordData = prop.get(10);
        OntProperty proOrganism = prop.get(11);
        OntProperty proProvince = prop.get(12);
        OntProperty proCountry = prop.get(13);
        OntProperty proTypeDbo = prop.get(14);
        OntProperty proSector = prop.get(15); 
        OntProperty proFuente = prop.get(16);
        OntProperty proFecha = prop.get(17);
        OntProperty proBlock = prop.get(18);        
        OntProperty proMedicalSpecialty = ontoModel.createOntProperty(rdfs+"medicalSpecialty");
        OntProperty ontProperty = ontoModel.createOntProperty(rdfs+"name");        
        OntProperty proName = ontoModel.createOntProperty(rdfs+"name");        
        //CLASES
//        OntClass claseParish = clas.get(0);
//        OntClass claseBlock = clas.get(1);
//        OntClass claseMedicalSpecialty = clas.get(2);
//        OntClass claseMedicalOrganization = clas.get(3);
//        OntClass claseCanton = clas.get(4);
//        OntClass claseProvince = clas.get(5);
//        OntClass claseCategory = clas.get(6);
//        OntClass claseCountry = clas.get(7);
//        OntClass clasePopulatePlace = clas.get(8);
//        OntClass claseDepartment = clas.get(9);
        OntClass claseOrganization = clas.get(10);
//        OntClass claseRecordData = clas.get(11);
        

         Resource centrosSalud = dboModel.createResource();
        for (int i = 0; i < centros.size(); i++) {
            centrosSalud = dboModel.createResource(rdfs + centros.get(i).getNombCentro())
                    .addProperty(RDF.type, ontoModel.getResource(claseOrganization.getURI()))
                    .addProperty(proId, centros.get(i).getId())
                    .addProperty(proName, centros.get(i).getNombCentro());

            Resource department = dboModel.createResource(dataPrefix + centros.get(i).getDepartamento())
                    .addProperty(RDF.type, ontoModel.getResource(claseOrganization.getURI()))
                    .addProperty(proSector, centros.get(i).getSector());

            Resource categoria = dboModel.createResource(dataPrefix + centros.get(i).getCategoria())
                    .addProperty(proTypeDbo, centros.get(i).getTipo());

            Resource country = dboModel.createResource(dbo + centros.get(i).getPais());

            Resource province = dboModel.createResource(dbo + centros.get(i).getProvincia() + ",Provincia");

            Resource canton = dboModel.createResource(dataPrefix + centros.get(i).getCanton() + ",Canton");

            Resource parish = dboModel.createResource(dataPrefix + centros.get(i).getParroquia() + ",Parroquia")
                    .addProperty(proArea, centros.get(i).getArea());
            
            Resource recordData = dboModel.createResource(dataPrefix + "registro" + i)
                    .addProperty(proFecha, centros.get(i).getAnio())
                    .addProperty(proFuente, centros.get(i).getFuente1());
            
            Resource specialty = dboModel.createResource();
            for (int j = 0; j < centros.get(i).getCantEspecialistas().size(); j += 2) {
                specialty = dboModel.createResource(dataPrefix+centros.get(i).getCantEspecialistas().get(j))               
                       .addProperty(ontProperty, centros.get(i).getCantEspecialistas().get(j))
                       .addProperty(proCantSpecialty, centros.get(i).getCantEspecialistas().get(j+1));   
                
                dboModel.add(recordData, proMedicalSpecialty, specialty);
            }
            
            Resource recordDataW = dboModel.createResource(dataPrefix + "Waste" + i)
                    .addProperty(proFecha, centros.get(i).getAnio())
                    .addProperty(proFuente, centros.get(i).getFuente2());
            
            Resource block = dboModel.createResource();
            for (int j = 0; j < centros.get(i).getCantBloque().size(); j += 3) {
                block = dboModel.createResource(dataPrefix + centros.get(i).getCantBloque().get(j))
                    .addProperty(proCode, centros.get(i).getCantBloque().get(j))
                    .addProperty(proDetail, centros.get(i).getCantBloque().get(j+1))
                    .addProperty(proCantBlock, centros.get(i).getCantBloque().get(j+2));
                
                dboModel.add(recordDataW, proBlock, block);
            }
            

            dboModel.add(centrosSalud, proParish, parish);
            dboModel.add(centrosSalud, proOrganism, department);
            dboModel.add(centrosSalud, proSubject, categoria);
            dboModel.add(centrosSalud, proRecordData, recordData);
            dboModel.add(centrosSalud, proRecordDataWaste, recordDataW);
            dboModel.add(centrosSalud, proRecordDataWaste, recordDataW);
            dboModel.add(province, proCountry, country);
            dboModel.add(canton, proProvince, province);
            dboModel.add(parish, proIsPartOf, canton);
            

//            StmtIterator iter = ontoModel.listStatements();
//            // print out the predicate, subject and object of each statement
//            while (iter.hasNext()) {
//                Statement stmt = iter.nextStatement();  // get next statement
//                Resource subject = stmt.getSubject();     // get the subject
//                Property predicate = stmt.getPredicate();   // get the predicate
//                RDFNode object = stmt.getObject();      // get the object
//                
//                //System.out.println("hola");
//                //System.out.print(subject.toString());
//                //System.out.println("fin");
//               // System.out.print(" " + predicate.toString() + " ");
//                if (object instanceof Resource) {
//                    //System.out.print(object.toString());
//                } else {
//                    // object is a literal
//                    //System.out.print(" \"" + object.toString() + "\"");
//                }
//
//                //System.out.println(" .");
//            }
}
            System.out.println("MODELO RDF------");            
            
            //RDFDataMgr.write(System.out, ontoModel, Lang.RDFXML) ;          
//ontoModel.write(System.out, Dataset, RDFFormat) ;
            dboModel.write(System.out, "RDF/XML-ABBREV");
            // Save to a file
            RDFWriter writer = dboModel.getWriter("RDF/XML"); //RDF/XML
            writer.write(dboModel, os, "");            
                
        dboModel.close();
        ontoModel.close();

    }

}
