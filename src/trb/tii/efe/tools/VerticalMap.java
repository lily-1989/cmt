package trb.tii.efe.tools;

import java.util.Hashtable;
import java.util.Vector;
import java.util.Enumeration;
import java.util.StringTokenizer;

import oracle.xml.parser.v2.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Method;
import java.lang.reflect.InvocationTargetException;

import org.w3c.dom.NodeList;

public class VerticalMap {
    // for class logic hash
    final static String DEFAULT_VERT_NAME = "_DEFAULT_";
    // for normalize hash
    final static String LABEL_KEY = "_LABEL_";

    // top level keys: DEFAULT_VERT_NAME, all vertical names from class-logic file, except the default
    protected Hashtable classLogic;

    // top level keys: LABEL_KEY, all vertical names from normalize file
    protected Hashtable normalizeLogic;
    protected Hashtable formattingClasses;
    // these two variables will be reset for each ad in setClassification()
    protected String selected_vert_id = null;
    protected String selected_vert_table = null;
    // Vertical fields mapped true/false if visible
    protected Hashtable visibleLogic = null;

    private String vmapMarket;
    private String vmapClassDir;
    private String vmapNormDir;

    public VerticalMap() {

    }

    public VerticalMap(String marketStr, String clsDir, String normDir) {
        vmapMarket = marketStr;
        vmapClassDir = clsDir;
        vmapNormDir = normDir;
    }


    protected Boolean loadConfigData() {

        // Set to false if data does not load
        Boolean loadCheck = new Boolean(true);

        try {
            classLogic = loadClassLogic(vmapClassDir + vmapMarket + "-clslogic.xml");
            Logger.log("Class-logic dir " + classLogic);
        } catch (Exception e) {
            loadCheck = new Boolean(false);
            Logger.log("WARNING: class logic config error, " + e.toString());
        }

        try {
            normalizeLogic = loadNormalize(vmapNormDir + vmapMarket + "-norm.xml");
            Logger.log("Norm dir: " + normalizeLogic);
        } catch (Exception e) {
            loadCheck = new Boolean(false);
            Logger.log("WARNING: normalize config error, " + e.toString());
        }

        return loadCheck;
    }

    protected String setClassification(String classStr) {
        // this method uses instance variable "classLogic", but should not modify it

        int classid=0;
        try {
                classid=Integer.parseInt(classStr,10);
        } catch (NumberFormatException e) {
                classid=0;
        }

        selected_vert_id = null;
        selected_vert_table = null;

        for ( Enumeration vertEnum = classLogic.keys(); vertEnum.hasMoreElements(); ) {
            // if these potential_* variables are set at the end of the loop,
            // then assign them to the selected_vert_* variables
            // if we set and unset the selected_vert_* as soon as we see an include or exclude match,
            //     one vertical's inclusion may be undone by another vertical's exclusion
            String potential_id = null;
            String potential_table = null;

            String this_vert_id = (String) vertEnum.nextElement();
            if ( this_vert_id.equals( DEFAULT_VERT_NAME ) ) {
              continue;  // do not process default vertical
            }

            Hashtable vert_data = (Hashtable) classLogic.get( this_vert_id );
            String this_vert_table = (String) vert_data.get("table");

            Vector include_ranges = (Vector) vert_data.get( "include-ranges" );
            Vector include_singles = (Vector) vert_data.get( "include-singles" );
            Vector exclude_ranges = (Vector) vert_data.get( "exclude-ranges" );
            Vector exclude_singles = (Vector) vert_data.get( "exclude-singles" );

            // check include ranges
            for (int i=0; i<include_ranges.size(); i++) {
              int[] range = (int[]) include_ranges.get(i);

              if (classid >= range[0] && classid <= range[1]) {
                potential_id = this_vert_id;
                potential_table = this_vert_table;
                break;
              }
            }

            // check include singles
            for (int i=0; i<include_singles.size(); i++) {
                int singleClass = ((Integer) include_singles.get(i)).intValue();
                if (classid == singleClass) {
                    potential_id = this_vert_id;
                    potential_table = this_vert_table;
                    break;
                }
            }

            // check exclude ranges
            for (int i=0; i<exclude_ranges.size(); i++) {
              int[] range = (int[]) exclude_ranges.get(i);

              if (classid >= range[0] && classid <= range[1]) {
                potential_id = null;
                potential_table = null;
                break;
              }
            }

            // check exclude singles
            for (int i=0; i<exclude_singles.size(); i++) {
                int singleClass = ((Integer) exclude_singles.get(i)).intValue();

                if (classid == singleClass) {
                    potential_id = null;
                    potential_table = null;
                    break;
                }
            }

            // only set this at the end of the loop, which means the class is included and not excluded by this vertical
            if (potential_id != null) {
              selected_vert_id = potential_id;
              selected_vert_table = potential_table;
            }
        }

        // if the class did not match any vertical, and there is a default vertical, choose the default
        if (selected_vert_id==null && classLogic.containsKey( DEFAULT_VERT_NAME )) {
          selected_vert_id = (String) ((Hashtable) classLogic.get(DEFAULT_VERT_NAME)).get("id");
          selected_vert_table = (String) ((Hashtable) classLogic.get(DEFAULT_VERT_NAME)).get("table");
        }

        /********/
        return selected_vert_id;
        /********/
    }

    protected String setTableName (String vert_id) {
        String vert_table = "";

        if (vert_id.equalsIgnoreCase("Jobs"))
            vert_table = "CCI_Jobs";
        else if (vert_id.equalsIgnoreCase("Apts"))
            vert_table = "CCI_Apts";
        else if (vert_id.equalsIgnoreCase("Cars"))
            vert_table = "CCI_Transport";
        else if (vert_id.equalsIgnoreCase("MiscTrans"))
            vert_table = "CCI_Transport";
        else if (vert_id.equalsIgnoreCase("Marine"))
            vert_table = "CCI_Transport";
        else if (vert_id.equalsIgnoreCase("RE"))
            vert_table = "CCI_RealEstate";
        else if (vert_id.equalsIgnoreCase("CommRE"))
            vert_table = "CCI_RealEstate";
        else if (vert_id.equalsIgnoreCase("Vital"))
            vert_table = "CCI_VitalNotice";
        else if (vert_id.equalsIgnoreCase("Pets"))
            vert_table = "CCI_GenClassified";
        else if (vert_id.equalsIgnoreCase("Garage"))
            vert_table = "CCI_GenClassified";
        else if (vert_id.equalsIgnoreCase("Merch"))
            vert_table = "CCI_GenClassified";
        else
            vert_table = "CCI_GenClassified";

        return vert_table;
    }

    // Edit ads
    protected Hashtable setNormalize() {
        String[] normalizeTable = null;

        // retrieve table for current market
        normalizeTable = (String[]) normalizeLogic.get(selected_vert_id);
        setVisibleFields (normalizeTable);

        return visibleLogic;
    }
    // Create ads
    protected Hashtable setNormalize(String vert_id) {
        String[] normalizeTable = null;

        // retrieve table for current market
        normalizeTable = (String[]) normalizeLogic.get(vert_id);
        setVisibleFields (normalizeTable);

        return visibleLogic;
    }



    public void setVisibleFields (String[] normalizeFields){
        Hashtable vis_data = new Hashtable (110);
        String key;
        String valueStr;
        Boolean value = null;

        int i = 0;
        for (i = 0; i<normalizeFields.length; i++) {
            key = normalizeFields[i].substring(0, normalizeFields[i].indexOf(" "));
            valueStr = normalizeFields[i].substring(normalizeFields[i].indexOf(" "));

            if (valueStr.indexOf(" -") > -1){
                value = new Boolean(false);
            }
            else
                value = new Boolean(true);

            vis_data.put(key, value );
        }
        visibleLogic = vis_data;
    }


    protected Hashtable loadClassLogic(String filename) throws Exception {
        BufferedReader reader = null;
        // throws FileNotFoundException
        reader = new BufferedReader(new FileReader(filename));

        DOMParser parser = new DOMParser();
        parser.setPreserveWhitespace(false);
        // throws XMLParseException, SAXException, IOException
        parser.parse( reader );

        XMLDocument doc = parser.getDocument();
        XMLElement root = (XMLElement) doc.getDocumentElement(); // class-logic node

        Hashtable vertbuckets = new Hashtable(20);
        String defaultvert_id = null;
        String defaultvert_table = null;

        NodeList vertmaps = null;
        // selectNodes() throws XSLException
        vertmaps = root.selectNodes("vertical-map");

        for (int vi=0; vi<vertmaps.getLength(); vi++) {
            XMLElement vert = (XMLElement) vertmaps.item(vi);
            NodeList include = null,
                    exclude = null;
            include = vert.selectNodes("class[@include='Y']");
            exclude = vert.selectNodes("class[@include='N']");

            String this_vert_id = vert.getAttribute("id");
            String this_vert_table = vert.getAttribute("table-name");

            // this chooses the last empty vertical-map as default
            if (include.getLength()==0 && exclude.getLength()==0) {
                if (defaultvert_id != null)
                    Logger.log("NOTE: Found more than one default (i.e. empty) vertical-map. The last one will be used as default.");
                defaultvert_id = this_vert_id;
                defaultvert_table = this_vert_table;
                continue;
            }

            Hashtable vert_data = new Hashtable(20);
            Vector include_ranges = new Vector();
            Vector include_singles = new Vector();
            Vector exclude_ranges = new Vector();
            Vector exclude_singles = new Vector();

            vertbuckets.put( this_vert_id, vert_data );
            vert_data.put( "table", this_vert_table );
            vert_data.put( "include-ranges", include_ranges );
            vert_data.put( "include-singles", include_singles );
            vert_data.put( "exclude-ranges", exclude_ranges );
            vert_data.put( "exclude-singles", exclude_singles );

            // include
            for (int ci=0; ci<include.getLength(); ci++) {
                XMLElement cls = (XMLElement) include.item(ci);
                if (! cls.getAttribute("range").equals("")) {  // range
                    int lower, upper;
                    String range = cls.getAttribute("range");
                    int dash_idx = range.indexOf("-");
                    lower = Integer.parseInt( range.substring(0, dash_idx) );
                    upper = Integer.parseInt( range.substring(dash_idx+1, range.length()) );
                    // log error if range is invalid
                    if (lower < 0 || upper < 0 || upper < lower)
                        Logger.log("Range is invalid: "+lower+", "+upper+". Proceeding anyway.");

                    // store into struct
                    include_ranges.add( new int[] { lower, upper } );

                } else if (! cls.getAttribute("single").equals("")) {  // single
                    int singleClass = Integer.parseInt( cls.getAttribute("single") );

                    // store into struct
                    include_singles.add( new Integer(singleClass) ); // remember to unpack before using
                }
            }

            // exclude
            for (int ci=0; ci<exclude.getLength(); ci++) {
                XMLElement cls = (XMLElement) exclude.item(ci);
                if (! cls.getAttribute("range").equals("")) {  // range
                    int lower, upper;
                    String range = cls.getAttribute("range");
                    int dash_idx = range.indexOf("-");
                    lower = Integer.parseInt( range.substring(0, dash_idx) );
                    upper = Integer.parseInt( range.substring(dash_idx+1, range.length()) );
                    // log error if range is invalid
                    if (lower < 0 || upper < 0 || upper < lower)
                        Logger.log("Range is invalid: "+lower+", "+upper+". Proceeding anyway.");

                    // store into struct
                    exclude_ranges.add( new int[] { lower, upper } );

                } else if (! cls.getAttribute("single").equals("")) {  // single
                    int singleClass = Integer.parseInt( cls.getAttribute("single") );

                    // store into struct
                    exclude_singles.add( new Integer(singleClass) );
                }
            }
        }

        // store default
        vertbuckets.put(DEFAULT_VERT_NAME, new Hashtable(5));
        ((Hashtable) vertbuckets.get(DEFAULT_VERT_NAME)).put("id", defaultvert_id);
        ((Hashtable) vertbuckets.get(DEFAULT_VERT_NAME)).put("table", defaultvert_table);


        return vertbuckets;
    }

    protected Hashtable loadNormalize(String filename) throws Exception {
        BufferedReader reader = null;
        reader = new BufferedReader(new FileReader(filename));

        DOMParser parser = new DOMParser();
        parser.setPreserveWhitespace(false);
        parser.parse( reader );

        XMLDocument doc = parser.getDocument();
        XMLElement root = (XMLElement) doc.getDocumentElement(); // normalize node

        Hashtable normalize_logic = new Hashtable(20);
        String label = root.getAttribute("label");
        normalize_logic.put( LABEL_KEY, label );

        NodeList vertmaps = root.selectNodes("vertical-map");

        for (int vi=0; vi<vertmaps.getLength(); vi++) {
            XMLElement vert = (XMLElement) vertmaps.item(vi);
            String cur_vert_id = vert.getAttribute("id");

            XMLElement tabledata = (XMLElement) vert.selectSingleNode("tabledata");
            XMLNode tabletext = (XMLNode) tabledata.selectSingleNode("text()");
            String tableString = tabletext.getNodeValue();

            StringTokenizer stok = new StringTokenizer(tableString, "\n\r");
            Vector lines = new Vector(100);
            String[] normalizeTable;

            while (stok.hasMoreTokens()) {
              String line = stok.nextToken();
              if ( line.trim().equals("") )  continue;  // skip blanks

              lines.add(line);
            }

            normalizeTable = new String[ lines.size() ];
            System.arraycopy( lines.toArray(), 0, normalizeTable, 0, lines.size() );

            normalize_logic.put( cur_vert_id, normalizeTable );
        }
        return normalize_logic;
    }
}

