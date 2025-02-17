/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * DlgReg.java
 *
 * Created on Jun 8, 2010, choose Tools | Templates
 * and open the template in10:28:56 PM
 */

package simrskhanza;
import permintaan.DlgBookingOperasi;
import bridging.BPJSDataSEP;
import bridging.DlgSKDPBPJS;
import bridging.BPJSSuratKontrol;
import bridging.SisruteRujukanKeluar;
import keuangan.DlgBilingRalan;
import fungsi.WarnaTable;
import fungsi.batasInput;
import fungsi.config;
import fungsi.koneksiDB;
import fungsi.sekuel;
import fungsi.validasi;
import fungsi.var;
import inventory.DlgPeresepanDokter;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import keuangan.DlgBilingParsialRalan;
import keuangan.DlgLhtPiutang;
import laporan.DlgBerkasRawat;
import laporan.DlgDataInsidenKeselamatan;
import laporan.DlgDiagnosaPenyakit;
import permintaan.DlgPermintaanLaboratorium;
import permintaan.DlgPermintaanRadiologi;

/**
 *
 * @author dosen
 */
public final class DlgIGD extends javax.swing.JDialog {
    private final DefaultTableModel tabMode;
    private sekuel Sequel=new sekuel();
    private validasi Valid=new validasi();
    private Connection koneksi=koneksiDB.condb();
    public  DlgPasien pasien=new DlgPasien(null,false);
    public  DlgCariDokter dokter=new DlgCariDokter(null,false);
    public  DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
    private DlgCatatan catatan=new DlgCatatan(null,false);
    private DlgRujukanPoliInternal dlgrjk=new DlgRujukanPoliInternal(null,false);
                    
    private PreparedStatement ps,ps2,ps3,pscaripiutang;
    private ResultSet rs;
    private boolean ceksukses=false;
    private int pilihan=0,i=0,jmlparsial=0;
    private Properties prop = new Properties();
    private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy/MM/dd");
    private double biaya=0,biayabaru=0,biayalama=0;
    private String kdigd="",nosisrute="",aktifkanparsial="no",URUTNOREG="",
            status="Baru",alamatperujuk="-",umur="0",sttsumur="Th",IPPRINTERTRACER="",
            validasiregistrasi=Sequel.cariIsi("select wajib_closing_kasir from set_validasi_registrasi"),
            validasicatatan=Sequel.cariIsi("select tampilkan_catatan from set_validasi_catatan");
    private char ESC = 27;
    // ganti kertas
    private char[] FORM_FEED = {12};
    // reset setting
    private char[] RESET = {ESC,'@'};
    // huruf tebal diaktifkan
    private char[] BOLD_ON = {ESC,'E'};
    // huruf tebal dimatikan
    private char[] BOLD_OFF = {ESC,'F'};
    // huruf miring diaktifkan
    private char[] ITALIC_ON = {ESC,'4'};
    // huruf miring dimatikan
    private char[] ITALIC_OFF = {ESC,'5'};
    // mode draft diaktifkan
    private char[] MODE_DRAFT = {ESC,'x',0};
    private char[] MODE_NLQ = {ESC,'x',1};
    // font Roman (halaman 47)
    private char[] FONT_ROMAN = {ESC,'k',0};
    // font Sans serif
    private char[] FONT_SANS_SERIF = {ESC,'k',1};
    // font size (halaman 49)
    private char[] SIZE_5_CPI = {ESC,'W','1',ESC,'P'};
    private char[] SIZE_6_CPI = {ESC,'W','1',ESC,'M'};
    private char[] SIZE_10_CPI = {ESC,'P'};
    private char[] SIZE_12_CPI = {ESC,'M'};
    //font height
    private char[] HEIGHT_NORMAL = {ESC,'w', '0'};
    private char[] HEIGHT_DOUBLE = {ESC,'w', '1'};
    // double strike (satu dot dicetak 2 kali)
    private char[] DOUBLE_STRIKE_ON = {ESC,'G'};
    private char[] DOUBLE_STRIKE_OFF = {ESC,'H'};
    // http://www.berklix.com/~jhs/standards/escapes.epson
    // condensed (huruf kurus)
    private char[] CONDENSED_ON = {15};
    private char[] CONDENSED_OFF = {18};
    // condensed (huruf gemuk)
    private char[] ENLARGED_ON = {(char) 14};
    private char[] ENLARGED_OFF = {(char) 20};
    // line spacing
    private char[] SPACING_9_72 = {ESC, '0'};
    private char[] SPACING_7_72 = {ESC, '1'};
    private char[] SPACING_12_72 = {ESC, '2'};
    // set unit for margin setting
    private char[] UNIT_1_360 = {ESC,40, 'U', '1', '0'};
    // move vertical print position
    private char[] VERTICAL_PRINT_POSITION = {ESC, 'J', '1'};

    /** Creates new form DlgReg
     * @param parent
     * @param modal */
    public DlgIGD(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        this.setLocation(8,1);
        setSize(885,674);

        Object[] row={"P","No.Reg","No.Rawat","Tanggal","Jam","Kd.Dokter","Dokter Dituju","Nomer RM",
            "Pasien","J.K.","Umur","Poliklinik","Penanggung Jawab","Alamat P.J.","Hubungan dg P.J.",
            "Biaya Registrasi","Status","Jenis Bayar","Stts Rawat","Kd PJ","Tgl.Lhr Px"};
        tabMode=new DefaultTableModel(null,row){
             @Override public boolean isCellEditable(int rowIndex, int colIndex){
                boolean a = false;
                if (colIndex==0) {
                    a=true;
                }
                return a;
             }
             Class[] types = new Class[] {
                 java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, 
                 java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class,java.lang.Object.class
             };
             @Override
             public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
             }
        };
        tbPetugas.setModel(tabMode);

        tbPetugas.setPreferredScrollableViewportSize(new Dimension(800,800));
        tbPetugas.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        for (i = 0; i < 21; i++) {
            TableColumn column = tbPetugas.getColumnModel().getColumn(i);
            if(i==0){
                column.setPreferredWidth(20);
            }else if(i==1){
                column.setPreferredWidth(50);
            }else if(i==2){
                column.setPreferredWidth(120);
            }else if(i==3){
                column.setPreferredWidth(70);
            }else if(i==4){
                column.setPreferredWidth(50);   
            }else if(i==5){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==6){
                column.setPreferredWidth(200);
            }else if(i==7){
                column.setPreferredWidth(70);
            }else if(i==8){
                column.setPreferredWidth(200);
            }else if(i==9){
                column.setPreferredWidth(30);
            }else if(i==10){
                column.setPreferredWidth(50);
            }else if(i==11){
                column.setPreferredWidth(140);
            }else if(i==12){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==13){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==14){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==15){
                column.setPreferredWidth(90);
            }else if(i==16){
                column.setPreferredWidth(100);
            }else if(i==17){
                column.setPreferredWidth(80);
            }else if(i==18){
                column.setPreferredWidth(70);
            }else if(i==19){
                column.setMinWidth(0);
                column.setMaxWidth(0);
            }else if(i==20){
                column.setPreferredWidth(70);
            }
            
            
        }
        tbPetugas.setDefaultRenderer(Object.class, new WarnaTable());

        TNoReg.setDocument(new batasInput((byte)8).getKata(TNoReg));
        TNoRw.setDocument(new batasInput((byte)17).getKata(TNoRw));
        TNoRM.setDocument(new batasInput((byte)10).getKata(TNoRM));
        kddokter.setDocument(new batasInput((byte)20).getKata(kddokter));
        kdpnj.setDocument(new batasInput((byte)3).getKata(kdpnj));
        TPngJwb.setDocument(new batasInput((byte)30).getKata(TPngJwb));
        AsalRujukan.setDocument(new batasInput((byte)60).getKata(AsalRujukan));
        THbngn.setDocument(new batasInput((byte)20).getKata(THbngn));
        TAlmt.setDocument(new batasInput((byte)60).getKata(TAlmt));
        TCari.setDocument(new batasInput((byte)100).getKata(TCari));
        if(config.cariCepat().equals("aktif")){
            TCari.getDocument().addDocumentListener(new javax.swing.event.DocumentListener(){
                @Override
                public void insertUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void removeUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
                @Override
                public void changedUpdate(DocumentEvent e) {
                    if(TCari.getText().length()>2){
                        tampil();
                    }
                }
            });
        } 
        jam();                        
        
        ChkInput.setSelected(false);
        isForm(); 
        
        pasien.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(pasien.getTable().getSelectedRow()!= -1){ 
                        TNoRM.setText(pasien.getTable().getValueAt(pasien.getTable().getSelectedRow(),1).toString());
                        isPas();
                        isNumber();                                        
                    }  
                    TNoRM.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        }); 
        
        rujukmasuk.WindowPerujuk.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgReg")){
                    if(rujukmasuk.tbPerujuk.getSelectedRow()!= -1){
                        AsalRujukan.setText(rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(),0).toString());
                        alamatperujuk=rujukmasuk.tbPerujuk.getValueAt(rujukmasuk.tbPerujuk.getSelectedRow(),1).toString();
                    }    
                    AsalRujukan.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        dokter.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {;}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(dokter.getTable().getSelectedRow()!= -1){    
                            kddokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),0).toString());
                            TDokter.setText(dokter.getTable().getValueAt(dokter.getTable().getSelectedRow(),1).toString());
                            isNumber();
                            kddokter.requestFocus();
                    }                
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
                           
        
        pasien.kab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(pasien.kab.getTable().getSelectedRow()!= -1){                   
                        Kabupaten2.setText(pasien.kab.getTable().getValueAt(pasien.kab.getTable().getSelectedRow(),0).toString());
                    }     
                    Kabupaten2.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.kec.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(pasien.kec.getTable().getSelectedRow()!= -1){                   
                        Kecamatan2.setText(pasien.kec.getTable().getValueAt(pasien.kec.getTable().getSelectedRow(),0).toString());
                    }                
                    Kecamatan2.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.kel.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(pasien.kel.getTable().getSelectedRow()!= -1){                   
                        Kelurahan2.setText(pasien.kel.getTable().getValueAt(pasien.kel.getTable().getSelectedRow(),0).toString());
                    }  
                    Kelurahan2.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.penjab.addWindowListener(new WindowListener() {
            @Override
            public void windowOpened(WindowEvent e) {}
            @Override
            public void windowClosing(WindowEvent e) {}
            @Override
            public void windowClosed(WindowEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(pasien.penjab.getTable().getSelectedRow()!= -1){
                        kdpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),1).toString());
                        nmpnj.setText(pasien.penjab.getTable().getValueAt(pasien.penjab.getTable().getSelectedRow(),2).toString());
                    }    
                    kdpnj.requestFocus();
                }
            }
            @Override
            public void windowIconified(WindowEvent e) {}
            @Override
            public void windowDeiconified(WindowEvent e) {}
            @Override
            public void windowActivated(WindowEvent e) {}
            @Override
            public void windowDeactivated(WindowEvent e) {}
        });
        
        pasien.penjab.getTable().addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {}
            @Override
            public void keyPressed(KeyEvent e) {
                if(var.getform().equals("DlgIGD")){
                    if(e.getKeyCode()==KeyEvent.VK_SPACE){
                        pasien.penjab.dispose();
                    }
                }
            }
            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        DlgCatatan.setSize(595,34);
        
        try {
            prop.loadFromXML(new FileInputStream("setting/config.xml"));
            IPPRINTERTRACER=prop.getProperty("IPPRINTERTRACER");
            URUTNOREG=prop.getProperty("URUTNOREG");            
            aktifkanparsial=prop.getProperty("AKTIFKANBILLINGPARSIAL");
            ps3=koneksi.prepareStatement("select * from poliklinik where kd_poli='IGDK'");
            try {
                rs=ps3.executeQuery();
                if(rs.next()){
                    biayabaru=rs.getDouble("registrasi");
                    biayalama=rs.getDouble("registrasilama");
                    kdigd=rs.getString("kd_poli");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }
            try{    
                if(prop.getProperty("MENUTRANSPARAN").equals("yes")){
                    //com.sun.awt.AWTUtilities.setWindowOpacity(DlgCatatan,0.5f);
                }     
            }catch(Exception e){    
            } 
        } catch (Exception ex) {
            IPPRINTERTRACER="";
            URUTNOREG="";
        }
    }
    
    

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        MnPermintaan = new javax.swing.JMenu();
        MnJadwalOperasi = new javax.swing.JMenuItem();
        MnSKDPBPJS = new javax.swing.JMenuItem();
        MnPermintaanLab = new javax.swing.JMenuItem();
        MnPermintaanRadiologi = new javax.swing.JMenuItem();
        MnSuratKontrolVclaim = new javax.swing.JMenuItem();
        MnTindakan = new javax.swing.JMenu();
        MnRawatJalan = new javax.swing.JMenuItem();
        MnPeriksaLab = new javax.swing.JMenuItem();
        MnPeriksaRadiologi = new javax.swing.JMenuItem();
        MnOperasi = new javax.swing.JMenuItem();
        MnObat = new javax.swing.JMenu();
        MnResepDOkter = new javax.swing.JMenuItem();
        MnNoResep = new javax.swing.JMenuItem();
        MnPemberianObat = new javax.swing.JMenuItem();
        MnPilihBilling = new javax.swing.JMenu();
        MnBillingParsial = new javax.swing.JMenuItem();
        MnBilling = new javax.swing.JMenuItem();
        MnRujukan = new javax.swing.JMenu();
        MnRujuk = new javax.swing.JMenuItem();
        MnRujukMasuk = new javax.swing.JMenuItem();
        MnPoliInternal = new javax.swing.JMenuItem();
        MnJawabanKonsul = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ppGrafikPerpoli = new javax.swing.JMenuItem();
        ppGrafikPerdokter = new javax.swing.JMenuItem();
        ppGrafikPerJK = new javax.swing.JMenuItem();
        ppGrafikPerPekerjaan = new javax.swing.JMenuItem();
        ppGrafikPerAgama = new javax.swing.JMenuItem();
        ppGrafikPerTahun = new javax.swing.JMenuItem();
        ppGrafikPerBulan = new javax.swing.JMenuItem();
        ppGrafikPerTanggal = new javax.swing.JMenuItem();
        ppGrafikDemografi = new javax.swing.JMenuItem();
        MnSurat = new javax.swing.JMenu();
        MnCetakSuratSakit = new javax.swing.JMenuItem();
        MnCetakRegister = new javax.swing.JMenuItem();
        MnPersetujuanMedis = new javax.swing.JMenuItem();
        MnLembarCasemix = new javax.swing.JMenuItem();
        MnSPBK = new javax.swing.JMenuItem();
        MnJKRA = new javax.swing.JMenuItem();
        MnLembarRalan = new javax.swing.JMenuItem();
        MnBlangkoResep = new javax.swing.JMenuItem();
        MnStatus = new javax.swing.JMenu();
        ppBerkas = new javax.swing.JMenuItem();
        MnSudah = new javax.swing.JMenuItem();
        MnBelum = new javax.swing.JMenuItem();
        MnBatal = new javax.swing.JMenuItem();
        MnDirujuk = new javax.swing.JMenuItem();
        MnDIrawat = new javax.swing.JMenuItem();
        MnMeninggal = new javax.swing.JMenuItem();
        MnPulangPaksa = new javax.swing.JMenuItem();
        jMenu7 = new javax.swing.JMenu();
        MnStatusBaru = new javax.swing.JMenuItem();
        MnStatusLama = new javax.swing.JMenuItem();
        MnGelang = new javax.swing.JMenu();
        MnGelang1 = new javax.swing.JMenuItem();
        MnLabelTracker = new javax.swing.JMenuItem();
        MnBarcode1 = new javax.swing.JMenuItem();
        MnBarcodeRM9 = new javax.swing.JMenuItem();
        MnBridging = new javax.swing.JMenu();
        MnSEP = new javax.swing.JMenuItem();
        MnRujukSisrute = new javax.swing.JMenuItem();
        MenuInputData = new javax.swing.JMenu();
        MnDiagnosa = new javax.swing.JMenuItem();
        ppCatatanPasien = new javax.swing.JMenuItem();
        ppBerkasDigital = new javax.swing.JMenuItem();
        ppIKP = new javax.swing.JMenuItem();
        ppRiwayat = new javax.swing.JMenuItem();
        ppDPJP = new javax.swing.JMenuItem();
        MnDataTriaseIGD = new javax.swing.JMenuItem();
        Kd2 = new widget.TextBox();
        DlgSakit = new javax.swing.JDialog();
        internalFrame3 = new widget.InternalFrame();
        panelBiasa2 = new widget.PanelBiasa();
        TglSakit1 = new widget.Tanggal();
        jLabel31 = new widget.Label();
        BtnPrint2 = new widget.Button();
        BtnKeluar2 = new widget.Button();
        jLabel32 = new widget.Label();
        TglSakit2 = new widget.Tanggal();
        jLabel33 = new widget.Label();
        lmsakit = new widget.TextBox();
        DlgDemografi = new javax.swing.JDialog();
        internalFrame4 = new widget.InternalFrame();
        panelBiasa3 = new widget.PanelBiasa();
        BtnPrint3 = new widget.Button();
        BtnKeluar3 = new widget.Button();
        Kelurahan2 = new widget.TextBox();
        btnKel = new widget.Button();
        Kecamatan2 = new widget.TextBox();
        btnKec = new widget.Button();
        Kabupaten2 = new widget.TextBox();
        btnKab = new widget.Button();
        jLabel34 = new widget.Label();
        jLabel35 = new widget.Label();
        jLabel36 = new widget.Label();
        BtnPrint4 = new widget.Button();
        DlgSakit2 = new javax.swing.JDialog();
        internalFrame5 = new widget.InternalFrame();
        panelBiasa4 = new widget.PanelBiasa();
        jLabel37 = new widget.Label();
        BtnPrint5 = new widget.Button();
        BtnKeluar4 = new widget.Button();
        NomorSurat = new widget.TextBox();
        BtnSeek5 = new widget.Button();
        CrDokter3 = new widget.TextBox();
        jLabel24 = new widget.Label();
        NoBalasan = new widget.TextBox();
        DlgCatatan = new javax.swing.JDialog();
        internalFrame6 = new widget.InternalFrame();
        LabelCatatan = new widget.Label();
        DlgJawabanKonsul = new javax.swing.JDialog();
        internalFrame8 = new widget.InternalFrame();
        jLabel26 = new widget.Label();
        jLabel27 = new widget.Label();
        LabelNama = new widget.Label();
        jLabel28 = new widget.Label();
        LabelNoRM = new widget.Label();
        jLabel29 = new widget.Label();
        LabelNoRW = new widget.Label();
        jLabel38 = new widget.Label();
        LabelDokter = new widget.Label();
        jLabel39 = new widget.Label();
        LabelPoliTujuan = new widget.Label();
        jLabel40 = new widget.Label();
        jLabel41 = new widget.Label();
        LabelDokterTujuan = new widget.Label();
        jLabel42 = new widget.Label();
        LabelPoliPerujuk = new widget.Label();
        jLabel43 = new widget.Label();
        jLabel44 = new widget.Label();
        jLabel45 = new widget.Label();
        LabelDiagnosa = new widget.Label();
        jLabel46 = new widget.Label();
        TSaranJawab = new widget.TextArea();
        TKonsulJawab = new widget.TextArea();
        TPemeriksaanJawab = new widget.TextArea();
        BtnKeluar6 = new widget.Button();
        internalFrame1 = new widget.InternalFrame();
        Scroll = new widget.ScrollPane();
        tbPetugas = new widget.Table();
        jPanel2 = new javax.swing.JPanel();
        panelGlass6 = new widget.panelisi();
        BtnSimpan = new widget.Button();
        BtnBatal = new widget.Button();
        BtnHapus = new widget.Button();
        BtnEdit = new widget.Button();
        BtnPrint = new widget.Button();
        BtnAll = new widget.Button();
        jLabel10 = new widget.Label();
        LCount = new widget.Label();
        BtnKeluar = new widget.Button();
        panelGlass7 = new widget.panelisi();
        jLabel15 = new widget.Label();
        DTPCari1 = new widget.Tanggal();
        jLabel17 = new widget.Label();
        DTPCari2 = new widget.Tanggal();
        jLabel6 = new widget.Label();
        TCari = new widget.TextBox();
        BtnCari = new widget.Button();
        PanelInput = new javax.swing.JPanel();
        FormInput = new widget.PanelBiasa();
        jLabel3 = new widget.Label();
        jLabel4 = new widget.Label();
        TDokter = new widget.TextBox();
        TNoRw = new widget.TextBox();
        jLabel8 = new widget.Label();
        jLabel13 = new widget.Label();
        jLabel9 = new widget.Label();
        DTPReg = new widget.Tanggal();
        jLabel20 = new widget.Label();
        jLabel21 = new widget.Label();
        TPngJwb = new widget.TextBox();
        TNoRM = new widget.TextBox();
        TNoReg = new widget.TextBox();
        CmbJam = new widget.ComboBox();
        CmbMenit = new widget.ComboBox();
        CmbDetik = new widget.ComboBox();
        jLabel7 = new widget.Label();
        TAlmt = new widget.TextBox();
        BtnPasien = new widget.Button();
        TPasien = new widget.TextBox();
        jLabel22 = new widget.Label();
        THbngn = new widget.TextBox();
        ChkJln = new widget.CekBox();
        kddokter = new widget.TextBox();
        BtnDokter = new widget.Button();
        jLabel30 = new widget.Label();
        TStatus = new widget.TextBox();
        jLabel18 = new widget.Label();
        kdpnj = new widget.TextBox();
        nmpnj = new widget.TextBox();
        btnPenjab = new widget.Button();
        AsalRujukan = new widget.TextBox();
        jLabel23 = new widget.Label();
        btnPenjab1 = new widget.Button();
        ChkTracker = new widget.CekBox();
        ChkInput = new widget.CekBox();

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N

        MnPermintaan.setBackground(new java.awt.Color(252, 255, 250));
        MnPermintaan.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPermintaan.setText("Permintaan");
        MnPermintaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaan.setIconTextGap(5);
        MnPermintaan.setName("MnPermintaan"); // NOI18N
        MnPermintaan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnJadwalOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJadwalOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnJadwalOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnJadwalOperasi.setText("Jadwal Operasi");
        MnJadwalOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJadwalOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJadwalOperasi.setIconTextGap(5);
        MnJadwalOperasi.setName("MnJadwalOperasi"); // NOI18N
        MnJadwalOperasi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnJadwalOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJadwalOperasiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnJadwalOperasi);

        MnSKDPBPJS.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSKDPBPJS.setForeground(new java.awt.Color(70, 70, 70));
        MnSKDPBPJS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSKDPBPJS.setText("SKDP BPJS");
        MnSKDPBPJS.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSKDPBPJS.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSKDPBPJS.setIconTextGap(5);
        MnSKDPBPJS.setName("MnSKDPBPJS"); // NOI18N
        MnSKDPBPJS.setPreferredSize(new java.awt.Dimension(170, 26));
        MnSKDPBPJS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSKDPBPJSActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnSKDPBPJS);

        MnPermintaanLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanLab.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPermintaanLab.setText("Pemeriksaan Lab");
        MnPermintaanLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanLab.setIconTextGap(5);
        MnPermintaanLab.setName("MnPermintaanLab"); // NOI18N
        MnPermintaanLab.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanLabActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanLab);

        MnPermintaanRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPermintaanRadiologi.setForeground(new java.awt.Color(70, 70, 70));
        MnPermintaanRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPermintaanRadiologi.setText("Pemeriksaan Radiologi");
        MnPermintaanRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPermintaanRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPermintaanRadiologi.setIconTextGap(5);
        MnPermintaanRadiologi.setName("MnPermintaanRadiologi"); // NOI18N
        MnPermintaanRadiologi.setPreferredSize(new java.awt.Dimension(170, 26));
        MnPermintaanRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPermintaanRadiologiActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnPermintaanRadiologi);

        MnSuratKontrolVclaim.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSuratKontrolVclaim.setForeground(new java.awt.Color(70, 70, 70));
        MnSuratKontrolVclaim.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSuratKontrolVclaim.setText("Surat Kontrol Vclaim");
        MnSuratKontrolVclaim.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSuratKontrolVclaim.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSuratKontrolVclaim.setIconTextGap(5);
        MnSuratKontrolVclaim.setName("MnSuratKontrolVclaim"); // NOI18N
        MnSuratKontrolVclaim.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSuratKontrolVclaim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSuratKontrolVclaimActionPerformed(evt);
            }
        });
        MnPermintaan.add(MnSuratKontrolVclaim);

        jPopupMenu1.add(MnPermintaan);

        MnTindakan.setBackground(new java.awt.Color(252, 255, 250));
        MnTindakan.setForeground(new java.awt.Color(70, 70, 70));
        MnTindakan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnTindakan.setText("Tindakan & Pemeriksaan");
        MnTindakan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnTindakan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnTindakan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnTindakan.setIconTextGap(5);
        MnTindakan.setName("MnTindakan"); // NOI18N
        MnTindakan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnRawatJalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRawatJalan.setForeground(new java.awt.Color(70, 70, 70));
        MnRawatJalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnRawatJalan.setText("Tagihan/Tindakan Rawat Jalan");
        MnRawatJalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRawatJalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRawatJalan.setIconTextGap(5);
        MnRawatJalan.setName("MnRawatJalan"); // NOI18N
        MnRawatJalan.setPreferredSize(new java.awt.Dimension(240, 26));
        MnRawatJalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRawatJalanActionPerformed(evt);
            }
        });
        MnTindakan.add(MnRawatJalan);

        MnPeriksaLab.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaLab.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaLab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPeriksaLab.setText("Periksa Laborat");
        MnPeriksaLab.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaLab.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaLab.setIconTextGap(5);
        MnPeriksaLab.setName("MnPeriksaLab"); // NOI18N
        MnPeriksaLab.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaLab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaLabActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaLab);

        MnPeriksaRadiologi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPeriksaRadiologi.setForeground(new java.awt.Color(70, 70, 70));
        MnPeriksaRadiologi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPeriksaRadiologi.setText("Periksa Radiologi");
        MnPeriksaRadiologi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPeriksaRadiologi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPeriksaRadiologi.setIconTextGap(5);
        MnPeriksaRadiologi.setName("MnPeriksaRadiologi"); // NOI18N
        MnPeriksaRadiologi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPeriksaRadiologi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPeriksaRadiologiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnPeriksaRadiologi);

        MnOperasi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnOperasi.setForeground(new java.awt.Color(70, 70, 70));
        MnOperasi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnOperasi.setText("Tagihan Operasi/VK");
        MnOperasi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnOperasi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnOperasi.setIconTextGap(5);
        MnOperasi.setName("MnOperasi"); // NOI18N
        MnOperasi.setPreferredSize(new java.awt.Dimension(240, 26));
        MnOperasi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnOperasiActionPerformed(evt);
            }
        });
        MnTindakan.add(MnOperasi);

        jPopupMenu1.add(MnTindakan);

        MnObat.setBackground(new java.awt.Color(252, 255, 250));
        MnObat.setForeground(new java.awt.Color(70, 70, 70));
        MnObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnObat.setText("Obat");
        MnObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnObat.setIconTextGap(5);
        MnObat.setName("MnObat"); // NOI18N
        MnObat.setPreferredSize(new java.awt.Dimension(250, 26));

        MnResepDOkter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnResepDOkter.setForeground(new java.awt.Color(70, 70, 70));
        MnResepDOkter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnResepDOkter.setText("Input Resep Dokter");
        MnResepDOkter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnResepDOkter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnResepDOkter.setIconTextGap(5);
        MnResepDOkter.setName("MnResepDOkter"); // NOI18N
        MnResepDOkter.setPreferredSize(new java.awt.Dimension(160, 23));
        MnResepDOkter.setRequestFocusEnabled(false);
        MnResepDOkter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnResepDOkterActionPerformed(evt);
            }
        });
        MnObat.add(MnResepDOkter);

        MnNoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnNoResep.setForeground(new java.awt.Color(70, 70, 70));
        MnNoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnNoResep.setText("Input No.Resep");
        MnNoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnNoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnNoResep.setIconTextGap(5);
        MnNoResep.setName("MnNoResep"); // NOI18N
        MnNoResep.setPreferredSize(new java.awt.Dimension(160, 23));
        MnNoResep.setRequestFocusEnabled(false);
        MnNoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnNoResepActionPerformed(evt);
            }
        });
        MnObat.add(MnNoResep);

        MnPemberianObat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPemberianObat.setForeground(new java.awt.Color(70, 70, 70));
        MnPemberianObat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPemberianObat.setText("Pemberian Obat/BHP");
        MnPemberianObat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPemberianObat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPemberianObat.setIconTextGap(5);
        MnPemberianObat.setName("MnPemberianObat"); // NOI18N
        MnPemberianObat.setPreferredSize(new java.awt.Dimension(160, 23));
        MnPemberianObat.setRequestFocusEnabled(false);
        MnPemberianObat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPemberianObatActionPerformed(evt);
            }
        });
        MnObat.add(MnPemberianObat);

        jPopupMenu1.add(MnObat);

        MnPilihBilling.setBackground(new java.awt.Color(252, 255, 250));
        MnPilihBilling.setForeground(new java.awt.Color(70, 70, 70));
        MnPilihBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPilihBilling.setText("Billing/Pembayaran Pasien");
        MnPilihBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPilihBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPilihBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPilihBilling.setIconTextGap(5);
        MnPilihBilling.setName("MnPilihBilling"); // NOI18N
        MnPilihBilling.setPreferredSize(new java.awt.Dimension(250, 26));

        MnBillingParsial.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBillingParsial.setForeground(new java.awt.Color(70, 70, 70));
        MnBillingParsial.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBillingParsial.setText("Billing Parsial");
        MnBillingParsial.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBillingParsial.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBillingParsial.setIconTextGap(5);
        MnBillingParsial.setName("MnBillingParsial"); // NOI18N
        MnBillingParsial.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBillingParsial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingParsialActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBillingParsial);

        MnBilling.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBilling.setForeground(new java.awt.Color(70, 70, 70));
        MnBilling.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBilling.setText("Billing Total");
        MnBilling.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBilling.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBilling.setIconTextGap(5);
        MnBilling.setName("MnBilling"); // NOI18N
        MnBilling.setPreferredSize(new java.awt.Dimension(130, 26));
        MnBilling.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBillingActionPerformed(evt);
            }
        });
        MnPilihBilling.add(MnBilling);

        jPopupMenu1.add(MnPilihBilling);

        MnRujukan.setBackground(new java.awt.Color(252, 255, 250));
        MnRujukan.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnRujukan.setText("Rujukan");
        MnRujukan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukan.setIconTextGap(5);
        MnRujukan.setName("MnRujukan"); // NOI18N
        MnRujukan.setPreferredSize(new java.awt.Dimension(250, 26));

        MnRujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnRujuk.setText("Rujukan Keluar");
        MnRujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujuk.setIconTextGap(5);
        MnRujuk.setName("MnRujuk"); // NOI18N
        MnRujuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujuk);

        MnRujukMasuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukMasuk.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukMasuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnRujukMasuk.setText("Rujukan Masuk");
        MnRujukMasuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukMasuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukMasuk.setIconTextGap(5);
        MnRujukMasuk.setName("MnRujukMasuk"); // NOI18N
        MnRujukMasuk.setPreferredSize(new java.awt.Dimension(160, 26));
        MnRujukMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukMasukActionPerformed(evt);
            }
        });
        MnRujukan.add(MnRujukMasuk);

        MnPoliInternal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPoliInternal.setForeground(new java.awt.Color(70, 70, 70));
        MnPoliInternal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPoliInternal.setText("Poli Internal");
        MnPoliInternal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPoliInternal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPoliInternal.setIconTextGap(5);
        MnPoliInternal.setName("MnPoliInternal"); // NOI18N
        MnPoliInternal.setPreferredSize(new java.awt.Dimension(160, 26));
        MnPoliInternal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPoliInternalActionPerformed(evt);
            }
        });
        MnRujukan.add(MnPoliInternal);

        MnJawabanKonsul.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJawabanKonsul.setForeground(new java.awt.Color(70, 70, 70));
        MnJawabanKonsul.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnJawabanKonsul.setText("Jawaban Konsul");
        MnJawabanKonsul.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJawabanKonsul.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJawabanKonsul.setIconTextGap(5);
        MnJawabanKonsul.setName("MnJawabanKonsul"); // NOI18N
        MnJawabanKonsul.setPreferredSize(new java.awt.Dimension(160, 26));
        MnJawabanKonsul.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJawabanKonsulActionPerformed(evt);
            }
        });
        MnRujukan.add(MnJawabanKonsul);

        jPopupMenu1.add(MnRujukan);

        jMenu2.setBackground(new java.awt.Color(252, 255, 250));
        jMenu2.setForeground(new java.awt.Color(70, 70, 70));
        jMenu2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        jMenu2.setText("Grafik Analisa");
        jMenu2.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu2.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu2.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu2.setIconTextGap(5);
        jMenu2.setName("jMenu2"); // NOI18N
        jMenu2.setPreferredSize(new java.awt.Dimension(250, 26));
        jMenu2.setRequestFocusEnabled(false);

        ppGrafikPerpoli.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerpoli.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerpoli.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerpoli.setText("Grafik Kunjungan Per Poli");
        ppGrafikPerpoli.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerpoli.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerpoli.setIconTextGap(5);
        ppGrafikPerpoli.setName("ppGrafikPerpoli"); // NOI18N
        ppGrafikPerpoli.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerpoli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerpoliActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerpoli);

        ppGrafikPerdokter.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerdokter.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerdokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerdokter.setText("Grafik Kunjungan Per Dokter");
        ppGrafikPerdokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerdokter.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerdokter.setIconTextGap(5);
        ppGrafikPerdokter.setName("ppGrafikPerdokter"); // NOI18N
        ppGrafikPerdokter.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerdokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerdokterActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerdokter);

        ppGrafikPerJK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerJK.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerJK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerJK.setText("Grafik Kunjungan Per Jenis Kelamin");
        ppGrafikPerJK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerJK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerJK.setIconTextGap(5);
        ppGrafikPerJK.setName("ppGrafikPerJK"); // NOI18N
        ppGrafikPerJK.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerJK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerJKActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerJK);

        ppGrafikPerPekerjaan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerPekerjaan.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerPekerjaan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerPekerjaan.setText("Grafik Kunjungan Per Pekerjaan");
        ppGrafikPerPekerjaan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerPekerjaan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerPekerjaan.setIconTextGap(5);
        ppGrafikPerPekerjaan.setName("ppGrafikPerPekerjaan"); // NOI18N
        ppGrafikPerPekerjaan.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerPekerjaan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerPekerjaanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerPekerjaan);

        ppGrafikPerAgama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerAgama.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerAgama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerAgama.setText("Grafik Kunjungan Per Agama");
        ppGrafikPerAgama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerAgama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerAgama.setIconTextGap(5);
        ppGrafikPerAgama.setName("ppGrafikPerAgama"); // NOI18N
        ppGrafikPerAgama.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerAgama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerAgamaActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerAgama);

        ppGrafikPerTahun.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTahun.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerTahun.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerTahun.setText("Grafik Kunjungan Per Tahun");
        ppGrafikPerTahun.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTahun.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTahun.setIconTextGap(5);
        ppGrafikPerTahun.setName("ppGrafikPerTahun"); // NOI18N
        ppGrafikPerTahun.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerTahun.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTahunActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTahun);

        ppGrafikPerBulan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerBulan.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerBulan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerBulan.setText("Grafik Kunjungan Per Bulan");
        ppGrafikPerBulan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerBulan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerBulan.setIconTextGap(5);
        ppGrafikPerBulan.setName("ppGrafikPerBulan"); // NOI18N
        ppGrafikPerBulan.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerBulan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerBulanActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerBulan);

        ppGrafikPerTanggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikPerTanggal.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikPerTanggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikPerTanggal.setText("Grafik Kunjungan Per Tanggal");
        ppGrafikPerTanggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikPerTanggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikPerTanggal.setIconTextGap(5);
        ppGrafikPerTanggal.setName("ppGrafikPerTanggal"); // NOI18N
        ppGrafikPerTanggal.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikPerTanggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikPerTanggalActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikPerTanggal);

        ppGrafikDemografi.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppGrafikDemografi.setForeground(new java.awt.Color(70, 70, 70));
        ppGrafikDemografi.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppGrafikDemografi.setText("Demografi Pendaftar");
        ppGrafikDemografi.setActionCommand("Grafik Demografi Pasien");
        ppGrafikDemografi.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppGrafikDemografi.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppGrafikDemografi.setIconTextGap(5);
        ppGrafikDemografi.setName("ppGrafikDemografi"); // NOI18N
        ppGrafikDemografi.setPreferredSize(new java.awt.Dimension(240, 26));
        ppGrafikDemografi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppGrafikDemografiActionPerformed(evt);
            }
        });
        jMenu2.add(ppGrafikDemografi);

        jPopupMenu1.add(jMenu2);

        MnSurat.setBackground(new java.awt.Color(252, 255, 250));
        MnSurat.setForeground(new java.awt.Color(70, 70, 70));
        MnSurat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSurat.setText("Surat-Surat");
        MnSurat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSurat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSurat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSurat.setIconTextGap(5);
        MnSurat.setName("MnSurat"); // NOI18N
        MnSurat.setPreferredSize(new java.awt.Dimension(250, 26));
        MnSurat.setRequestFocusEnabled(false);

        MnCetakSuratSakit.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakSuratSakit.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakSuratSakit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnCetakSuratSakit.setText("Surat Sakit");
        MnCetakSuratSakit.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakSuratSakit.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakSuratSakit.setIconTextGap(5);
        MnCetakSuratSakit.setName("MnCetakSuratSakit"); // NOI18N
        MnCetakSuratSakit.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCetakSuratSakit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakSuratSakitActionPerformed(evt);
            }
        });
        MnSurat.add(MnCetakSuratSakit);

        MnCetakRegister.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnCetakRegister.setForeground(new java.awt.Color(70, 70, 70));
        MnCetakRegister.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnCetakRegister.setText("Bukti Register");
        MnCetakRegister.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnCetakRegister.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnCetakRegister.setIconTextGap(5);
        MnCetakRegister.setName("MnCetakRegister"); // NOI18N
        MnCetakRegister.setPreferredSize(new java.awt.Dimension(240, 26));
        MnCetakRegister.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnCetakRegisterActionPerformed(evt);
            }
        });
        MnSurat.add(MnCetakRegister);

        MnPersetujuanMedis.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPersetujuanMedis.setForeground(new java.awt.Color(70, 70, 70));
        MnPersetujuanMedis.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPersetujuanMedis.setText("Persetujuan Medis");
        MnPersetujuanMedis.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPersetujuanMedis.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPersetujuanMedis.setIconTextGap(5);
        MnPersetujuanMedis.setName("MnPersetujuanMedis"); // NOI18N
        MnPersetujuanMedis.setPreferredSize(new java.awt.Dimension(240, 26));
        MnPersetujuanMedis.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPersetujuanMedisActionPerformed(evt);
            }
        });
        MnSurat.add(MnPersetujuanMedis);

        MnLembarCasemix.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarCasemix.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarCasemix.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnLembarCasemix.setText("Lembar Casemix");
        MnLembarCasemix.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarCasemix.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarCasemix.setIconTextGap(5);
        MnLembarCasemix.setName("MnLembarCasemix"); // NOI18N
        MnLembarCasemix.setPreferredSize(new java.awt.Dimension(240, 26));
        MnLembarCasemix.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarCasemixActionPerformed(evt);
            }
        });
        MnSurat.add(MnLembarCasemix);

        MnSPBK.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSPBK.setForeground(new java.awt.Color(70, 70, 70));
        MnSPBK.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSPBK.setText("Surat Bukti Pelayanan Kesehatan (SBPK)");
        MnSPBK.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSPBK.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSPBK.setIconTextGap(5);
        MnSPBK.setName("MnSPBK"); // NOI18N
        MnSPBK.setPreferredSize(new java.awt.Dimension(240, 26));
        MnSPBK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSPBKActionPerformed(evt);
            }
        });
        MnSurat.add(MnSPBK);

        MnJKRA.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnJKRA.setForeground(new java.awt.Color(70, 70, 70));
        MnJKRA.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnJKRA.setText("Surat Jaminan Kesehatan Nasional (JKRA) Rawat Jalan");
        MnJKRA.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnJKRA.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnJKRA.setIconTextGap(5);
        MnJKRA.setName("MnJKRA"); // NOI18N
        MnJKRA.setPreferredSize(new java.awt.Dimension(320, 26));
        MnJKRA.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnJKRAActionPerformed(evt);
            }
        });
        MnSurat.add(MnJKRA);

        MnLembarRalan.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLembarRalan.setForeground(new java.awt.Color(70, 70, 70));
        MnLembarRalan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnLembarRalan.setMnemonic('w');
        MnLembarRalan.setText("Lembar Rawat Jalan");
        MnLembarRalan.setToolTipText("W");
        MnLembarRalan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLembarRalan.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLembarRalan.setIconTextGap(5);
        MnLembarRalan.setName("MnLembarRalan"); // NOI18N
        MnLembarRalan.setPreferredSize(new java.awt.Dimension(320, 26));
        MnLembarRalan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLembarRalanActionPerformed(evt);
            }
        });
        MnSurat.add(MnLembarRalan);

        MnBlangkoResep.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBlangkoResep.setForeground(new java.awt.Color(70, 70, 70));
        MnBlangkoResep.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBlangkoResep.setMnemonic('w');
        MnBlangkoResep.setText("Blanko Resep");
        MnBlangkoResep.setToolTipText("W");
        MnBlangkoResep.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBlangkoResep.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBlangkoResep.setIconTextGap(5);
        MnBlangkoResep.setName("MnBlangkoResep"); // NOI18N
        MnBlangkoResep.setPreferredSize(new java.awt.Dimension(320, 26));
        MnBlangkoResep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBlangkoResepActionPerformed(evt);
            }
        });
        MnSurat.add(MnBlangkoResep);

        jPopupMenu1.add(MnSurat);

        MnStatus.setBackground(new java.awt.Color(252, 255, 250));
        MnStatus.setForeground(new java.awt.Color(70, 70, 70));
        MnStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnStatus.setText("Set Status");
        MnStatus.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatus.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatus.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatus.setIconTextGap(5);
        MnStatus.setName("MnStatus"); // NOI18N
        MnStatus.setPreferredSize(new java.awt.Dimension(250, 26));

        ppBerkas.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkas.setForeground(new java.awt.Color(70, 70, 70));
        ppBerkas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppBerkas.setText("Berkas R.M. Diterima");
        ppBerkas.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkas.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkas.setIconTextGap(5);
        ppBerkas.setName("ppBerkas"); // NOI18N
        ppBerkas.setPreferredSize(new java.awt.Dimension(220, 26));
        ppBerkas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(ppBerkas);

        MnSudah.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSudah.setForeground(new java.awt.Color(70, 70, 70));
        MnSudah.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSudah.setText("Sudah Periksa");
        MnSudah.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSudah.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSudah.setIconTextGap(5);
        MnSudah.setName("MnSudah"); // NOI18N
        MnSudah.setPreferredSize(new java.awt.Dimension(220, 26));
        MnSudah.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSudahActionPerformed(evt);
            }
        });
        MnStatus.add(MnSudah);

        MnBelum.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBelum.setForeground(new java.awt.Color(70, 70, 70));
        MnBelum.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBelum.setText("Belum Periksa");
        MnBelum.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBelum.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBelum.setIconTextGap(5);
        MnBelum.setName("MnBelum"); // NOI18N
        MnBelum.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBelum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBelumActionPerformed(evt);
            }
        });
        MnStatus.add(MnBelum);

        MnBatal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBatal.setForeground(new java.awt.Color(70, 70, 70));
        MnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBatal.setText("Batal Periksa");
        MnBatal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBatal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBatal.setIconTextGap(5);
        MnBatal.setName("MnBatal"); // NOI18N
        MnBatal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBatalActionPerformed(evt);
            }
        });
        MnStatus.add(MnBatal);

        MnDirujuk.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDirujuk.setForeground(new java.awt.Color(70, 70, 70));
        MnDirujuk.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnDirujuk.setText("Dirujuk");
        MnDirujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDirujuk.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDirujuk.setIconTextGap(5);
        MnDirujuk.setName("MnDirujuk"); // NOI18N
        MnDirujuk.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDirujuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDirujukActionPerformed(evt);
            }
        });
        MnStatus.add(MnDirujuk);

        MnDIrawat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDIrawat.setForeground(new java.awt.Color(70, 70, 70));
        MnDIrawat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnDIrawat.setText("Dirawat");
        MnDIrawat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDIrawat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDIrawat.setIconTextGap(5);
        MnDIrawat.setName("MnDIrawat"); // NOI18N
        MnDIrawat.setPreferredSize(new java.awt.Dimension(220, 26));
        MnDIrawat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDIrawatActionPerformed(evt);
            }
        });
        MnStatus.add(MnDIrawat);

        MnMeninggal.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnMeninggal.setForeground(new java.awt.Color(70, 70, 70));
        MnMeninggal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnMeninggal.setText("Meninggal");
        MnMeninggal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnMeninggal.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnMeninggal.setIconTextGap(5);
        MnMeninggal.setName("MnMeninggal"); // NOI18N
        MnMeninggal.setPreferredSize(new java.awt.Dimension(220, 26));
        MnMeninggal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnMeninggalActionPerformed(evt);
            }
        });
        MnStatus.add(MnMeninggal);

        MnPulangPaksa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnPulangPaksa.setForeground(new java.awt.Color(70, 70, 70));
        MnPulangPaksa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnPulangPaksa.setText("Pulang Paksa");
        MnPulangPaksa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnPulangPaksa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnPulangPaksa.setIconTextGap(5);
        MnPulangPaksa.setName("MnPulangPaksa"); // NOI18N
        MnPulangPaksa.setPreferredSize(new java.awt.Dimension(180, 26));
        MnPulangPaksa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnPulangPaksaBtnPrintActionPerformed(evt);
            }
        });
        MnStatus.add(MnPulangPaksa);

        jMenu7.setForeground(new java.awt.Color(70, 70, 70));
        jMenu7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        jMenu7.setText("Status Poli");
        jMenu7.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        jMenu7.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jMenu7.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        jMenu7.setIconTextGap(5);
        jMenu7.setName("jMenu7"); // NOI18N
        jMenu7.setPreferredSize(new java.awt.Dimension(240, 26));

        MnStatusBaru.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusBaru.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusBaru.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnStatusBaru.setText("Status Poli Baru");
        MnStatusBaru.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusBaru.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusBaru.setIconTextGap(5);
        MnStatusBaru.setName("MnStatusBaru"); // NOI18N
        MnStatusBaru.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusBaru.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusBaruActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusBaru);

        MnStatusLama.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnStatusLama.setForeground(new java.awt.Color(70, 70, 70));
        MnStatusLama.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnStatusLama.setText("Status Poli Lama");
        MnStatusLama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnStatusLama.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnStatusLama.setIconTextGap(5);
        MnStatusLama.setName("MnStatusLama"); // NOI18N
        MnStatusLama.setPreferredSize(new java.awt.Dimension(170, 26));
        MnStatusLama.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnStatusLamaActionPerformed(evt);
            }
        });
        jMenu7.add(MnStatusLama);

        MnStatus.add(jMenu7);

        jPopupMenu1.add(MnStatus);

        MnGelang.setBackground(new java.awt.Color(252, 255, 250));
        MnGelang.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnGelang.setText("Label & Barcode");
        MnGelang.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang.setIconTextGap(5);
        MnGelang.setName("MnGelang"); // NOI18N
        MnGelang.setPreferredSize(new java.awt.Dimension(250, 26));
        MnGelang.setRequestFocusEnabled(false);

        MnGelang1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnGelang1.setForeground(new java.awt.Color(70, 70, 70));
        MnGelang1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnGelang1.setText("Gelang Pasien");
        MnGelang1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnGelang1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnGelang1.setIconTextGap(5);
        MnGelang1.setName("MnGelang1"); // NOI18N
        MnGelang1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnGelang1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnGelang1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnGelang1);

        MnLabelTracker.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnLabelTracker.setForeground(new java.awt.Color(70, 70, 70));
        MnLabelTracker.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnLabelTracker.setText("Label Tracker 1");
        MnLabelTracker.setActionCommand("Label Tracker");
        MnLabelTracker.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnLabelTracker.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnLabelTracker.setIconTextGap(5);
        MnLabelTracker.setName("MnLabelTracker"); // NOI18N
        MnLabelTracker.setPreferredSize(new java.awt.Dimension(200, 26));
        MnLabelTracker.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnLabelTrackerActionPerformed(evt);
            }
        });
        MnGelang.add(MnLabelTracker);

        MnBarcode1.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcode1.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcode1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBarcode1.setText("Barcode Perawatan 1");
        MnBarcode1.setActionCommand("Barcode Perawatan");
        MnBarcode1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBarcode1.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBarcode1.setIconTextGap(5);
        MnBarcode1.setName("MnBarcode1"); // NOI18N
        MnBarcode1.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcode1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcode1ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcode1);

        MnBarcodeRM9.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBarcodeRM9.setForeground(new java.awt.Color(70, 70, 70));
        MnBarcodeRM9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBarcodeRM9.setMnemonic('L');
        MnBarcodeRM9.setText("Label Rekam Medis 10");
        MnBarcodeRM9.setToolTipText("L");
        MnBarcodeRM9.setIconTextGap(5);
        MnBarcodeRM9.setName("MnBarcodeRM9"); // NOI18N
        MnBarcodeRM9.setPreferredSize(new java.awt.Dimension(200, 26));
        MnBarcodeRM9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnBarcodeRM9ActionPerformed(evt);
            }
        });
        MnGelang.add(MnBarcodeRM9);

        jPopupMenu1.add(MnGelang);

        MnBridging.setBackground(new java.awt.Color(252, 255, 250));
        MnBridging.setForeground(new java.awt.Color(70, 70, 70));
        MnBridging.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnBridging.setText("Bridging");
        MnBridging.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnBridging.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnBridging.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnBridging.setIconTextGap(5);
        MnBridging.setName("MnBridging"); // NOI18N
        MnBridging.setPreferredSize(new java.awt.Dimension(250, 26));

        MnSEP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnSEP.setForeground(new java.awt.Color(70, 70, 70));
        MnSEP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnSEP.setText("SEP BPJS");
        MnSEP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnSEP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnSEP.setIconTextGap(5);
        MnSEP.setName("MnSEP"); // NOI18N
        MnSEP.setPreferredSize(new java.awt.Dimension(180, 26));
        MnSEP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnSEPActionPerformed(evt);
            }
        });
        MnBridging.add(MnSEP);

        MnRujukSisrute.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnRujukSisrute.setForeground(new java.awt.Color(70, 70, 70));
        MnRujukSisrute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnRujukSisrute.setText("Rujuk Keluar Via Sisrute");
        MnRujukSisrute.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnRujukSisrute.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnRujukSisrute.setIconTextGap(5);
        MnRujukSisrute.setName("MnRujukSisrute"); // NOI18N
        MnRujukSisrute.setPreferredSize(new java.awt.Dimension(180, 26));
        MnRujukSisrute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnRujukSisruteActionPerformed(evt);
            }
        });
        MnBridging.add(MnRujukSisrute);

        jPopupMenu1.add(MnBridging);

        MenuInputData.setBackground(new java.awt.Color(252, 255, 250));
        MenuInputData.setForeground(new java.awt.Color(70, 70, 70));
        MenuInputData.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MenuInputData.setText("Input Data");
        MenuInputData.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MenuInputData.setIconTextGap(5);
        MenuInputData.setName("MenuInputData"); // NOI18N
        MenuInputData.setPreferredSize(new java.awt.Dimension(250, 26));

        MnDiagnosa.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDiagnosa.setForeground(new java.awt.Color(70, 70, 70));
        MnDiagnosa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnDiagnosa.setText("Diagnosa Pasien");
        MnDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDiagnosa.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDiagnosa.setIconTextGap(5);
        MnDiagnosa.setName("MnDiagnosa"); // NOI18N
        MnDiagnosa.setPreferredSize(new java.awt.Dimension(240, 26));
        MnDiagnosa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDiagnosaActionPerformed(evt);
            }
        });
        MenuInputData.add(MnDiagnosa);

        ppCatatanPasien.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppCatatanPasien.setForeground(new java.awt.Color(70, 70, 70));
        ppCatatanPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppCatatanPasien.setText("Catatan Untuk Pasien");
        ppCatatanPasien.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppCatatanPasien.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppCatatanPasien.setIconTextGap(5);
        ppCatatanPasien.setName("ppCatatanPasien"); // NOI18N
        ppCatatanPasien.setPreferredSize(new java.awt.Dimension(240, 26));
        ppCatatanPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppCatatanPasienBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppCatatanPasien);

        ppBerkasDigital.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppBerkasDigital.setForeground(new java.awt.Color(70, 70, 70));
        ppBerkasDigital.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppBerkasDigital.setText("Berkas Digital Perawatan");
        ppBerkasDigital.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppBerkasDigital.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppBerkasDigital.setIconTextGap(5);
        ppBerkasDigital.setName("ppBerkasDigital"); // NOI18N
        ppBerkasDigital.setPreferredSize(new java.awt.Dimension(240, 26));
        ppBerkasDigital.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppBerkasDigitalBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppBerkasDigital);

        ppIKP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppIKP.setForeground(new java.awt.Color(70, 70, 70));
        ppIKP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppIKP.setText("Insiden Keselamatan Pasien");
        ppIKP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppIKP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppIKP.setIconTextGap(5);
        ppIKP.setName("ppIKP"); // NOI18N
        ppIKP.setPreferredSize(new java.awt.Dimension(240, 26));
        ppIKP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppIKPBtnPrintActionPerformed(evt);
            }
        });
        MenuInputData.add(ppIKP);

        jPopupMenu1.add(MenuInputData);

        ppRiwayat.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppRiwayat.setForeground(new java.awt.Color(70, 70, 70));
        ppRiwayat.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppRiwayat.setText("Riwayat Perawatan");
        ppRiwayat.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppRiwayat.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppRiwayat.setIconTextGap(5);
        ppRiwayat.setName("ppRiwayat"); // NOI18N
        ppRiwayat.setPreferredSize(new java.awt.Dimension(250, 26));
        ppRiwayat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppRiwayatBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppRiwayat);

        ppDPJP.setBackground(new java.awt.Color(255, 255, 254));
        ppDPJP.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        ppDPJP.setForeground(new java.awt.Color(70, 70, 70));
        ppDPJP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        ppDPJP.setText("Input DPJP");
        ppDPJP.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ppDPJP.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ppDPJP.setIconTextGap(5);
        ppDPJP.setName("ppDPJP"); // NOI18N
        ppDPJP.setPreferredSize(new java.awt.Dimension(250, 26));
        ppDPJP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ppDPJPBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ppDPJP);

        MnDataTriaseIGD.setBackground(new java.awt.Color(255, 255, 254));
        MnDataTriaseIGD.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        MnDataTriaseIGD.setForeground(new java.awt.Color(70, 70, 70));
        MnDataTriaseIGD.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/category.png"))); // NOI18N
        MnDataTriaseIGD.setText("Data Triase Gawat Darurat");
        MnDataTriaseIGD.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        MnDataTriaseIGD.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        MnDataTriaseIGD.setIconTextGap(5);
        MnDataTriaseIGD.setName("MnDataTriaseIGD"); // NOI18N
        MnDataTriaseIGD.setPreferredSize(new java.awt.Dimension(250, 26));
        MnDataTriaseIGD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MnDataTriaseIGDBtnPrintActionPerformed(evt);
            }
        });
        jPopupMenu1.add(MnDataTriaseIGD);

        Kd2.setName("Kd2"); // NOI18N
        Kd2.setPreferredSize(new java.awt.Dimension(207, 23));

        DlgSakit.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit.setName("DlgSakit"); // NOI18N
        DlgSakit.setUndecorated(true);
        DlgSakit.setResizable(false);

        internalFrame3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Sakit ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame3.setName("internalFrame3"); // NOI18N
        internalFrame3.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa2.setName("panelBiasa2"); // NOI18N
        panelBiasa2.setLayout(null);

        TglSakit1.setEditable(false);
        TglSakit1.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2021" }));
        TglSakit1.setDisplayFormat("dd-MM-yyyy");
        TglSakit1.setName("TglSakit1"); // NOI18N
        TglSakit1.setOpaque(false);
        panelBiasa2.add(TglSakit1);
        TglSakit1.setBounds(70, 10, 100, 23);

        jLabel31.setText("Lama Sakit :");
        jLabel31.setName("jLabel31"); // NOI18N
        panelBiasa2.add(jLabel31);
        jLabel31.setBounds(297, 10, 110, 23);

        BtnPrint2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint2.setMnemonic('T');
        BtnPrint2.setText("Cetak");
        BtnPrint2.setToolTipText("Alt+T");
        BtnPrint2.setName("BtnPrint2"); // NOI18N
        BtnPrint2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnPrint2);
        BtnPrint2.setBounds(10, 50, 100, 30);

        BtnKeluar2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        BtnKeluar2.setMnemonic('K');
        BtnKeluar2.setText("Keluar");
        BtnKeluar2.setToolTipText("Alt+K");
        BtnKeluar2.setName("BtnKeluar2"); // NOI18N
        BtnKeluar2.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar2ActionPerformed(evt);
            }
        });
        panelBiasa2.add(BtnKeluar2);
        BtnKeluar2.setBounds(430, 50, 100, 30);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("s/d");
        jLabel32.setName("jLabel32"); // NOI18N
        panelBiasa2.add(jLabel32);
        jLabel32.setBounds(176, 10, 20, 23);

        TglSakit2.setEditable(false);
        TglSakit2.setForeground(new java.awt.Color(50, 70, 50));
        TglSakit2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2021" }));
        TglSakit2.setDisplayFormat("dd-MM-yyyy");
        TglSakit2.setName("TglSakit2"); // NOI18N
        TglSakit2.setOpaque(false);
        panelBiasa2.add(TglSakit2);
        TglSakit2.setBounds(200, 10, 100, 23);

        jLabel33.setText("Tanggal :");
        jLabel33.setName("jLabel33"); // NOI18N
        panelBiasa2.add(jLabel33);
        jLabel33.setBounds(0, 10, 66, 23);

        lmsakit.setHighlighter(null);
        lmsakit.setName("lmsakit"); // NOI18N
        panelBiasa2.add(lmsakit);
        lmsakit.setBounds(410, 10, 110, 23);

        internalFrame3.add(panelBiasa2, java.awt.BorderLayout.CENTER);

        DlgSakit.getContentPane().add(internalFrame3, java.awt.BorderLayout.CENTER);

        DlgDemografi.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgDemografi.setName("DlgDemografi"); // NOI18N
        DlgDemografi.setUndecorated(true);
        DlgDemografi.setResizable(false);

        internalFrame4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Demografi Pendaftar ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame4.setName("internalFrame4"); // NOI18N
        internalFrame4.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa3.setName("panelBiasa3"); // NOI18N
        panelBiasa3.setLayout(null);

        BtnPrint3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint3.setMnemonic('G');
        BtnPrint3.setText("Grafik");
        BtnPrint3.setToolTipText("Alt+G");
        BtnPrint3.setName("BtnPrint3"); // NOI18N
        BtnPrint3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint3ActionPerformed(evt);
            }
        });
        BtnPrint3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint3KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnPrint3);
        BtnPrint3.setBounds(110, 110, 100, 30);

        BtnKeluar3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        BtnKeluar3.setMnemonic('K');
        BtnKeluar3.setText("Keluar");
        BtnKeluar3.setToolTipText("Alt+K");
        BtnKeluar3.setName("BtnKeluar3"); // NOI18N
        BtnKeluar3.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar3ActionPerformed(evt);
            }
        });
        BtnKeluar3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar3KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnKeluar3);
        BtnKeluar3.setBounds(430, 110, 100, 30);

        Kelurahan2.setHighlighter(null);
        Kelurahan2.setName("Kelurahan2"); // NOI18N
        Kelurahan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kelurahan2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kelurahan2);
        Kelurahan2.setBounds(105, 70, 350, 23);

        btnKel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnKel.setMnemonic('1');
        btnKel.setToolTipText("ALt+1");
        btnKel.setName("btnKel"); // NOI18N
        btnKel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKelActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKel);
        btnKel.setBounds(460, 70, 28, 23);

        Kecamatan2.setHighlighter(null);
        Kecamatan2.setName("Kecamatan2"); // NOI18N
        Kecamatan2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kecamatan2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kecamatan2);
        Kecamatan2.setBounds(105, 40, 350, 23);

        btnKec.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnKec.setMnemonic('1');
        btnKec.setToolTipText("ALt+1");
        btnKec.setName("btnKec"); // NOI18N
        btnKec.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKecActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKec);
        btnKec.setBounds(460, 40, 28, 23);

        Kabupaten2.setHighlighter(null);
        Kabupaten2.setName("Kabupaten2"); // NOI18N
        Kabupaten2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                Kabupaten2KeyPressed(evt);
            }
        });
        panelBiasa3.add(Kabupaten2);
        Kabupaten2.setBounds(105, 10, 350, 23);

        btnKab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnKab.setMnemonic('1');
        btnKab.setToolTipText("ALt+1");
        btnKab.setName("btnKab"); // NOI18N
        btnKab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnKabActionPerformed(evt);
            }
        });
        panelBiasa3.add(btnKab);
        btnKab.setBounds(460, 10, 28, 23);

        jLabel34.setText("Kelurahan :");
        jLabel34.setName("jLabel34"); // NOI18N
        panelBiasa3.add(jLabel34);
        jLabel34.setBounds(0, 70, 100, 23);

        jLabel35.setText("Kabupaten :");
        jLabel35.setName("jLabel35"); // NOI18N
        panelBiasa3.add(jLabel35);
        jLabel35.setBounds(0, 10, 100, 23);

        jLabel36.setText("Kecamatan :");
        jLabel36.setName("jLabel36"); // NOI18N
        panelBiasa3.add(jLabel36);
        jLabel36.setBounds(0, 40, 100, 23);

        BtnPrint4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint4.setMnemonic('T');
        BtnPrint4.setText("Cetak");
        BtnPrint4.setToolTipText("Alt+T");
        BtnPrint4.setName("BtnPrint4"); // NOI18N
        BtnPrint4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint4ActionPerformed(evt);
            }
        });
        BtnPrint4.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrint4KeyPressed(evt);
            }
        });
        panelBiasa3.add(BtnPrint4);
        BtnPrint4.setBounds(10, 110, 100, 30);

        internalFrame4.add(panelBiasa3, java.awt.BorderLayout.CENTER);

        DlgDemografi.getContentPane().add(internalFrame4, java.awt.BorderLayout.CENTER);

        DlgSakit2.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgSakit2.setName("DlgSakit2"); // NOI18N
        DlgSakit2.setUndecorated(true);
        DlgSakit2.setResizable(false);

        internalFrame5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(230, 235, 225)), "::[ Cetak Surat Keterangan Rawat Inap ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(50, 70, 50))); // NOI18N
        internalFrame5.setName("internalFrame5"); // NOI18N
        internalFrame5.setLayout(new java.awt.BorderLayout(1, 1));

        panelBiasa4.setName("panelBiasa4"); // NOI18N
        panelBiasa4.setLayout(null);

        jLabel37.setText("Nomor Surat Keterangan :");
        jLabel37.setName("jLabel37"); // NOI18N
        panelBiasa4.add(jLabel37);
        jLabel37.setBounds(7, 10, 140, 23);

        BtnPrint5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint5.setMnemonic('T');
        BtnPrint5.setText("Cetak");
        BtnPrint5.setToolTipText("Alt+T");
        BtnPrint5.setName("BtnPrint5"); // NOI18N
        BtnPrint5.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrint5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnPrint5);
        BtnPrint5.setBounds(10, 80, 100, 30);

        BtnKeluar4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        BtnKeluar4.setMnemonic('K');
        BtnKeluar4.setText("Keluar");
        BtnKeluar4.setToolTipText("Alt+K");
        BtnKeluar4.setName("BtnKeluar4"); // NOI18N
        BtnKeluar4.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar4ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnKeluar4);
        BtnKeluar4.setBounds(430, 80, 100, 30);

        NomorSurat.setHighlighter(null);
        NomorSurat.setName("NomorSurat"); // NOI18N
        panelBiasa4.add(NomorSurat);
        NomorSurat.setBounds(150, 10, 370, 23);

        BtnSeek5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        BtnSeek5.setMnemonic('6');
        BtnSeek5.setToolTipText("ALt+6");
        BtnSeek5.setName("BtnSeek5"); // NOI18N
        BtnSeek5.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnSeek5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSeek5ActionPerformed(evt);
            }
        });
        panelBiasa4.add(BtnSeek5);
        BtnSeek5.setBounds(492, 40, 28, 23);

        CrDokter3.setEditable(false);
        CrDokter3.setName("CrDokter3"); // NOI18N
        CrDokter3.setPreferredSize(new java.awt.Dimension(300, 23));
        panelBiasa4.add(CrDokter3);
        CrDokter3.setBounds(150, 40, 340, 23);

        jLabel24.setText("Dokter Png. Jawab :");
        jLabel24.setName("jLabel24"); // NOI18N
        jLabel24.setPreferredSize(new java.awt.Dimension(60, 23));
        panelBiasa4.add(jLabel24);
        jLabel24.setBounds(7, 40, 140, 23);

        internalFrame5.add(panelBiasa4, java.awt.BorderLayout.CENTER);

        DlgSakit2.getContentPane().add(internalFrame5, java.awt.BorderLayout.CENTER);

        NoBalasan.setHighlighter(null);
        NoBalasan.setName("NoBalasan"); // NOI18N

        DlgCatatan.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgCatatan.setName("DlgCatatan"); // NOI18N
        DlgCatatan.setUndecorated(true);
        DlgCatatan.setResizable(false);

        internalFrame6.setBorder(null);
        internalFrame6.setName("internalFrame6"); // NOI18N
        internalFrame6.setWarnaAtas(new java.awt.Color(100, 100, 10));
        internalFrame6.setWarnaBawah(new java.awt.Color(100, 100, 10));
        internalFrame6.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                internalFrame6MouseClicked(evt);
            }
        });
        internalFrame6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        LabelCatatan.setForeground(new java.awt.Color(255, 255, 255));
        LabelCatatan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCatatan.setText("Catatan");
        LabelCatatan.setName("LabelCatatan"); // NOI18N
        LabelCatatan.setPreferredSize(new java.awt.Dimension(580, 23));
        LabelCatatan.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                LabelCatatanMouseClicked(evt);
            }
        });
        internalFrame6.add(LabelCatatan);

        DlgCatatan.getContentPane().add(internalFrame6, java.awt.BorderLayout.CENTER);

        DlgJawabanKonsul.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        DlgJawabanKonsul.setName("DlgJawabanKonsul"); // NOI18N
        DlgJawabanKonsul.setUndecorated(true);
        DlgJawabanKonsul.setResizable(false);

        internalFrame8.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Jawaban Konsul ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame8.setName("internalFrame8"); // NOI18N

        jLabel26.setText("DETAIL KONSUL / RUJUKAN INTERNAL :");
        jLabel26.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel26.setName("jLabel26"); // NOI18N

        jLabel27.setText("Nama Lengkap :");
        jLabel27.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel27.setName("jLabel27"); // NOI18N

        LabelNama.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNama.setText("Nama");
        LabelNama.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNama.setName("LabelNama"); // NOI18N

        jLabel28.setText("No. RM :");
        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel28.setName("jLabel28"); // NOI18N

        LabelNoRM.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNoRM.setText("NoRM");
        LabelNoRM.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNoRM.setName("LabelNoRM"); // NOI18N

        jLabel29.setText("No. Rawat :");
        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel29.setName("jLabel29"); // NOI18N

        LabelNoRW.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelNoRW.setText("NoRW");
        LabelNoRW.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelNoRW.setName("LabelNoRW"); // NOI18N

        jLabel38.setText("Dokter Perujuk :");
        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel38.setName("jLabel38"); // NOI18N

        LabelDokter.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDokter.setText("Nama Dokter");
        LabelDokter.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelDokter.setName("LabelDokter"); // NOI18N

        jLabel39.setText("Poli Tujuan :");
        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel39.setName("jLabel39"); // NOI18N

        LabelPoliTujuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelPoliTujuan.setText("NamaPoli");
        LabelPoliTujuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelPoliTujuan.setName("LabelPoliTujuan"); // NOI18N

        jLabel40.setText("DETAIL PASIEN :");
        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel40.setName("jLabel40"); // NOI18N

        jLabel41.setText("Dokter Tujuan :");
        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel41.setName("jLabel41"); // NOI18N

        LabelDokterTujuan.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDokterTujuan.setText("NamaDokterTujuan");
        LabelDokterTujuan.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelDokterTujuan.setName("LabelDokterTujuan"); // NOI18N

        jLabel42.setText("Poli Perujuk :");
        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel42.setName("jLabel42"); // NOI18N

        LabelPoliPerujuk.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelPoliPerujuk.setText("NamaPoli");
        LabelPoliPerujuk.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        LabelPoliPerujuk.setName("LabelPoliPerujuk"); // NOI18N

        jLabel43.setText("Catatan Konsul :");
        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel43.setName("jLabel43"); // NOI18N

        jLabel44.setText("Pemeriksaan :");
        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel44.setName("jLabel44"); // NOI18N

        jLabel45.setText("Diagnosa :");
        jLabel45.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel45.setName("jLabel45"); // NOI18N

        LabelDiagnosa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDiagnosa.setText("Diagnosa");
        LabelDiagnosa.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        LabelDiagnosa.setName("LabelDiagnosa"); // NOI18N

        jLabel46.setText("Saran :");
        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel46.setName("jLabel46"); // NOI18N

        TSaranJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TSaranJawab.setColumns(20);
        TSaranJawab.setRows(5);
        TSaranJawab.setText("Saran\n");
        TSaranJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TSaranJawab.setName("TSaranJawab"); // NOI18N
        TSaranJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TSaranJawabKeyPressed(evt);
            }
        });

        TKonsulJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TKonsulJawab.setColumns(20);
        TKonsulJawab.setRows(5);
        TKonsulJawab.setText("Konsul\n");
        TKonsulJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TKonsulJawab.setName("TKonsulJawab"); // NOI18N
        TKonsulJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TKonsulJawabKeyPressed(evt);
            }
        });

        TPemeriksaanJawab.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        TPemeriksaanJawab.setColumns(20);
        TPemeriksaanJawab.setRows(5);
        TPemeriksaanJawab.setText("Pemeriksaan\n");
        TPemeriksaanJawab.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        TPemeriksaanJawab.setName("TPemeriksaanJawab"); // NOI18N
        TPemeriksaanJawab.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPemeriksaanJawabKeyPressed(evt);
            }
        });

        BtnKeluar6.setMnemonic('K');
        BtnKeluar6.setText("Tutup");
        BtnKeluar6.setToolTipText("Alt+K");
        BtnKeluar6.setName("BtnKeluar6"); // NOI18N
        BtnKeluar6.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluar6ActionPerformed(evt);
            }
        });
        BtnKeluar6.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluar6KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout internalFrame8Layout = new javax.swing.GroupLayout(internalFrame8);
        internalFrame8.setLayout(internalFrame8Layout);
        internalFrame8Layout.setHorizontalGroup(
            internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame8Layout.createSequentialGroup()
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(internalFrame8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(internalFrame8Layout.createSequentialGroup()
                                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelNoRM, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(internalFrame8Layout.createSequentialGroup()
                                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(LabelNoRW, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LabelNama, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(381, 381, 381))
                    .addGroup(internalFrame8Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1269, 1269, 1269))
                    .addGroup(internalFrame8Layout.createSequentialGroup()
                        .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(internalFrame8Layout.createSequentialGroup()
                                        .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(internalFrame8Layout.createSequentialGroup()
                                        .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(LabelPoliPerujuk, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(internalFrame8Layout.createSequentialGroup()
                                        .addGap(12, 12, 12)
                                        .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(LabelPoliTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                                .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(LabelDokterTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addGroup(internalFrame8Layout.createSequentialGroup()
                                                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(6, 6, 6)
                                                    .addComponent(TSaranJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(internalFrame8Layout.createSequentialGroup()
                                                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(6, 6, 6)
                                                    .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                        .addComponent(TPemeriksaanJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                        .addComponent(TKonsulJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 391, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                            .addGroup(internalFrame8Layout.createSequentialGroup()
                                                .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(LabelDiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(internalFrame8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnKeluar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        internalFrame8Layout.setVerticalGroup(
            internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(internalFrame8Layout.createSequentialGroup()
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNama, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNoRM, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel29, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelNoRW, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDokter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPoliPerujuk, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDokterTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelPoliTujuan, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(internalFrame8Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(TKonsulJawab, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel44, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TPemeriksaanJawab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel45, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(LabelDiagnosa, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(internalFrame8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TSaranJawab, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BtnKeluar6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout DlgJawabanKonsulLayout = new javax.swing.GroupLayout(DlgJawabanKonsul.getContentPane());
        DlgJawabanKonsul.getContentPane().setLayout(DlgJawabanKonsulLayout);
        DlgJawabanKonsulLayout.setHorizontalGroup(
            DlgJawabanKonsulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, DlgJawabanKonsulLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(internalFrame8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        DlgJawabanKonsulLayout.setVerticalGroup(
            DlgJawabanKonsulLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(DlgJawabanKonsulLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(internalFrame8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        internalFrame1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(240, 245, 235)), "::[ Registrasi IGD Hari Ini ]::", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12), new java.awt.Color(70, 70, 70))); // NOI18N
        internalFrame1.setName("internalFrame1"); // NOI18N
        internalFrame1.setLayout(new java.awt.BorderLayout(1, 1));

        Scroll.setToolTipText("Klik data di table, kemudian klik kanan untuk menampilkan menu yang dibutuhkan");
        Scroll.setComponentPopupMenu(jPopupMenu1);
        Scroll.setName("Scroll"); // NOI18N
        Scroll.setOpaque(true);

        tbPetugas.setAutoCreateRowSorter(true);
        tbPetugas.setToolTipText("Klik data di table, kemudian klik kanan untuk menampilkan menu yang dibutuhkan");
        tbPetugas.setComponentPopupMenu(jPopupMenu1);
        tbPetugas.setName("tbPetugas"); // NOI18N
        tbPetugas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPetugasMouseClicked(evt);
            }
        });
        tbPetugas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbPetugasKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbPetugasKeyReleased(evt);
            }
        });
        Scroll.setViewportView(tbPetugas);

        internalFrame1.add(Scroll, java.awt.BorderLayout.CENTER);

        jPanel2.setName("jPanel2"); // NOI18N
        jPanel2.setOpaque(false);
        jPanel2.setLayout(new java.awt.BorderLayout(1, 1));

        panelGlass6.setName("panelGlass6"); // NOI18N
        panelGlass6.setPreferredSize(new java.awt.Dimension(55, 55));
        panelGlass6.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        BtnSimpan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/save-16x16.png"))); // NOI18N
        BtnSimpan.setMnemonic('S');
        BtnSimpan.setText("Simpan");
        BtnSimpan.setToolTipText("Alt+S");
        BtnSimpan.setName("BtnSimpan"); // NOI18N
        BtnSimpan.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnSimpanActionPerformed(evt);
            }
        });
        BtnSimpan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnSimpanKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnSimpan);

        BtnBatal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Cancel-2-16x16.png"))); // NOI18N
        BtnBatal.setMnemonic('B');
        BtnBatal.setText("Baru");
        BtnBatal.setToolTipText("Alt+B");
        BtnBatal.setName("BtnBatal"); // NOI18N
        BtnBatal.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnBatal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnBatalActionPerformed(evt);
            }
        });
        BtnBatal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnBatalKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnBatal);

        BtnHapus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/stop_f2.png"))); // NOI18N
        BtnHapus.setMnemonic('H');
        BtnHapus.setText("Hapus");
        BtnHapus.setToolTipText("Alt+H");
        BtnHapus.setName("BtnHapus"); // NOI18N
        BtnHapus.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnHapus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnHapusActionPerformed(evt);
            }
        });
        BtnHapus.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnHapusKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnHapus);

        BtnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/inventaris.png"))); // NOI18N
        BtnEdit.setMnemonic('G');
        BtnEdit.setText("Ganti");
        BtnEdit.setToolTipText("Alt+G");
        BtnEdit.setName("BtnEdit"); // NOI18N
        BtnEdit.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditActionPerformed(evt);
            }
        });
        BtnEdit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnEditKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnEdit);

        BtnPrint.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/b_print.png"))); // NOI18N
        BtnPrint.setMnemonic('T');
        BtnPrint.setText("Cetak");
        BtnPrint.setToolTipText("Alt+T");
        BtnPrint.setName("BtnPrint"); // NOI18N
        BtnPrint.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPrintActionPerformed(evt);
            }
        });
        BtnPrint.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnPrintKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnPrint);

        BtnAll.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/Search-16x16.png"))); // NOI18N
        BtnAll.setMnemonic('M');
        BtnAll.setText("Semua");
        BtnAll.setToolTipText("Alt+M");
        BtnAll.setName("BtnAll"); // NOI18N
        BtnAll.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnAll.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAllActionPerformed(evt);
            }
        });
        BtnAll.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnAllKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnAll);

        jLabel10.setText("Record :");
        jLabel10.setName("jLabel10"); // NOI18N
        jLabel10.setPreferredSize(new java.awt.Dimension(70, 30));
        panelGlass6.add(jLabel10);

        LCount.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LCount.setText("0");
        LCount.setName("LCount"); // NOI18N
        LCount.setPreferredSize(new java.awt.Dimension(72, 30));
        panelGlass6.add(LCount);

        BtnKeluar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/exit.png"))); // NOI18N
        BtnKeluar.setMnemonic('K');
        BtnKeluar.setText("Keluar");
        BtnKeluar.setToolTipText("Alt+K");
        BtnKeluar.setName("BtnKeluar"); // NOI18N
        BtnKeluar.setPreferredSize(new java.awt.Dimension(100, 30));
        BtnKeluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnKeluarActionPerformed(evt);
            }
        });
        BtnKeluar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnKeluarKeyPressed(evt);
            }
        });
        panelGlass6.add(BtnKeluar);

        jPanel2.add(panelGlass6, java.awt.BorderLayout.PAGE_END);

        panelGlass7.setName("panelGlass7"); // NOI18N
        panelGlass7.setPreferredSize(new java.awt.Dimension(44, 44));
        panelGlass7.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 9));

        jLabel15.setText("Periode :");
        jLabel15.setName("jLabel15"); // NOI18N
        jLabel15.setPreferredSize(new java.awt.Dimension(60, 23));
        panelGlass7.add(jLabel15);

        DTPCari1.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2021" }));
        DTPCari1.setDisplayFormat("dd-MM-yyyy");
        DTPCari1.setName("DTPCari1"); // NOI18N
        DTPCari1.setOpaque(false);
        DTPCari1.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari1);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setText("s.d");
        jLabel17.setName("jLabel17"); // NOI18N
        jLabel17.setPreferredSize(new java.awt.Dimension(24, 23));
        panelGlass7.add(jLabel17);

        DTPCari2.setForeground(new java.awt.Color(50, 70, 50));
        DTPCari2.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2021" }));
        DTPCari2.setDisplayFormat("dd-MM-yyyy");
        DTPCari2.setName("DTPCari2"); // NOI18N
        DTPCari2.setOpaque(false);
        DTPCari2.setPreferredSize(new java.awt.Dimension(133, 23));
        panelGlass7.add(DTPCari2);

        jLabel6.setText("Key Word :");
        jLabel6.setName("jLabel6"); // NOI18N
        jLabel6.setPreferredSize(new java.awt.Dimension(158, 23));
        panelGlass7.add(jLabel6);

        TCari.setName("TCari"); // NOI18N
        TCari.setPreferredSize(new java.awt.Dimension(300, 23));
        TCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TCariKeyPressed(evt);
            }
        });
        panelGlass7.add(TCari);

        BtnCari.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/accept.png"))); // NOI18N
        BtnCari.setMnemonic('7');
        BtnCari.setToolTipText("Alt+7");
        BtnCari.setName("BtnCari"); // NOI18N
        BtnCari.setPreferredSize(new java.awt.Dimension(28, 23));
        BtnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCariActionPerformed(evt);
            }
        });
        BtnCari.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                BtnCariKeyPressed(evt);
            }
        });
        panelGlass7.add(BtnCari);

        jPanel2.add(panelGlass7, java.awt.BorderLayout.CENTER);

        internalFrame1.add(jPanel2, java.awt.BorderLayout.PAGE_END);

        PanelInput.setName("PanelInput"); // NOI18N
        PanelInput.setOpaque(false);
        PanelInput.setLayout(new java.awt.BorderLayout(1, 1));

        FormInput.setName("FormInput"); // NOI18N
        FormInput.setPreferredSize(new java.awt.Dimension(490, 167));
        FormInput.setLayout(null);

        jLabel3.setText("No. Reg. :");
        jLabel3.setName("jLabel3"); // NOI18N
        FormInput.add(jLabel3);
        jLabel3.setBounds(0, 12, 70, 23);

        jLabel4.setText("No. Rawat :");
        jLabel4.setName("jLabel4"); // NOI18N
        FormInput.add(jLabel4);
        jLabel4.setBounds(0, 42, 70, 23);

        TDokter.setEditable(false);
        TDokter.setName("TDokter"); // NOI18N
        FormInput.add(TDokter);
        TDokter.setBounds(183, 102, 209, 23);

        TNoRw.setHighlighter(null);
        TNoRw.setName("TNoRw"); // NOI18N
        TNoRw.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRwKeyPressed(evt);
            }
        });
        FormInput.add(TNoRw);
        TNoRw.setBounds(74, 42, 190, 23);

        jLabel8.setText("Tgl. Reg. :");
        jLabel8.setName("jLabel8"); // NOI18N
        FormInput.add(jLabel8);
        jLabel8.setBounds(0, 72, 70, 23);

        jLabel13.setText("Dr Dituju :");
        jLabel13.setName("jLabel13"); // NOI18N
        FormInput.add(jLabel13);
        jLabel13.setBounds(0, 102, 70, 23);
        jLabel13.getAccessibleContext().setAccessibleDescription("");

        jLabel9.setText("Jam :");
        jLabel9.setName("jLabel9"); // NOI18N
        FormInput.add(jLabel9);
        jLabel9.setBounds(165, 72, 36, 23);

        DTPReg.setForeground(new java.awt.Color(50, 70, 50));
        DTPReg.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "07-08-2021" }));
        DTPReg.setDisplayFormat("dd-MM-yyyy");
        DTPReg.setName("DTPReg"); // NOI18N
        DTPReg.setOpaque(false);
        DTPReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                DTPRegKeyPressed(evt);
            }
        });
        FormInput.add(DTPReg);
        DTPReg.setBounds(74, 72, 90, 23);

        jLabel20.setText("Png. Jawab :");
        jLabel20.setName("jLabel20"); // NOI18N
        FormInput.add(jLabel20);
        jLabel20.setBounds(416, 42, 100, 23);

        jLabel21.setText("Almt Png. Jwb :");
        jLabel21.setName("jLabel21"); // NOI18N
        FormInput.add(jLabel21);
        jLabel21.setBounds(426, 72, 90, 23);

        TPngJwb.setHighlighter(null);
        TPngJwb.setName("TPngJwb"); // NOI18N
        TPngJwb.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TPngJwbKeyPressed(evt);
            }
        });
        FormInput.add(TPngJwb);
        TPngJwb.setBounds(520, 42, 150, 23);

        TNoRM.setHighlighter(null);
        TNoRM.setName("TNoRM"); // NOI18N
        TNoRM.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRMKeyPressed(evt);
            }
        });
        FormInput.add(TNoRM);
        TNoRM.setBounds(520, 12, 110, 23);

        TNoReg.setHighlighter(null);
        TNoReg.setName("TNoReg"); // NOI18N
        TNoReg.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TNoRegKeyPressed(evt);
            }
        });
        FormInput.add(TNoReg);
        TNoReg.setBounds(74, 12, 120, 23);

        CmbJam.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23" }));
        CmbJam.setName("CmbJam"); // NOI18N
        CmbJam.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbJamKeyPressed(evt);
            }
        });
        FormInput.add(CmbJam);
        CmbJam.setBounds(205, 72, 62, 23);

        CmbMenit.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbMenit.setName("CmbMenit"); // NOI18N
        CmbMenit.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbMenitKeyPressed(evt);
            }
        });
        FormInput.add(CmbMenit);
        CmbMenit.setBounds(270, 72, 62, 23);

        CmbDetik.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "00", "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59" }));
        CmbDetik.setName("CmbDetik"); // NOI18N
        CmbDetik.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                CmbDetikKeyPressed(evt);
            }
        });
        FormInput.add(CmbDetik);
        CmbDetik.setBounds(335, 72, 62, 23);

        jLabel7.setText("No.Rekam Medik :");
        jLabel7.setName("jLabel7"); // NOI18N
        FormInput.add(jLabel7);
        jLabel7.setBounds(416, 12, 100, 23);

        TAlmt.setHighlighter(null);
        TAlmt.setName("TAlmt"); // NOI18N
        TAlmt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TAlmtKeyPressed(evt);
            }
        });
        FormInput.add(TAlmt);
        TAlmt.setBounds(520, 72, 170, 23);

        BtnPasien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        BtnPasien.setMnemonic('1');
        BtnPasien.setToolTipText("ALt+1");
        BtnPasien.setName("BtnPasien"); // NOI18N
        BtnPasien.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPasienActionPerformed(evt);
            }
        });
        FormInput.add(BtnPasien);
        BtnPasien.setBounds(852, 12, 28, 23);

        TPasien.setEditable(false);
        TPasien.setHighlighter(null);
        TPasien.setName("TPasien"); // NOI18N
        FormInput.add(TPasien);
        TPasien.setBounds(632, 12, 218, 23);

        jLabel22.setText("Hubungan :");
        jLabel22.setName("jLabel22"); // NOI18N
        FormInput.add(jLabel22);
        jLabel22.setBounds(648, 42, 98, 23);

        THbngn.setHighlighter(null);
        THbngn.setName("THbngn"); // NOI18N
        THbngn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                THbngnKeyPressed(evt);
            }
        });
        FormInput.add(THbngn);
        THbngn.setBounds(750, 42, 130, 23);

        ChkJln.setBorder(null);
        ChkJln.setSelected(true);
        ChkJln.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkJln.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkJln.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkJln.setName("ChkJln"); // NOI18N
        ChkJln.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkJlnActionPerformed(evt);
            }
        });
        FormInput.add(ChkJln);
        ChkJln.setBounds(400, 72, 23, 23);

        kddokter.setHighlighter(null);
        kddokter.setName("kddokter"); // NOI18N
        kddokter.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kddokterKeyPressed(evt);
            }
        });
        FormInput.add(kddokter);
        kddokter.setBounds(74, 102, 107, 23);

        BtnDokter.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        BtnDokter.setMnemonic('3');
        BtnDokter.setToolTipText("ALt+3");
        BtnDokter.setName("BtnDokter"); // NOI18N
        BtnDokter.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnDokterActionPerformed(evt);
            }
        });
        FormInput.add(BtnDokter);
        BtnDokter.setBounds(395, 102, 28, 23);

        jLabel30.setText("Status :");
        jLabel30.setName("jLabel30"); // NOI18N
        FormInput.add(jLabel30);
        jLabel30.setBounds(648, 72, 98, 23);

        TStatus.setEditable(false);
        TStatus.setHighlighter(null);
        TStatus.setName("TStatus"); // NOI18N
        FormInput.add(TStatus);
        TStatus.setBounds(750, 72, 130, 23);

        jLabel18.setText("Jenis Bayar :");
        jLabel18.setName("jLabel18"); // NOI18N
        FormInput.add(jLabel18);
        jLabel18.setBounds(426, 102, 90, 23);

        kdpnj.setHighlighter(null);
        kdpnj.setName("kdpnj"); // NOI18N
        kdpnj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                kdpnjKeyPressed(evt);
            }
        });
        FormInput.add(kdpnj);
        kdpnj.setBounds(520, 102, 70, 23);

        nmpnj.setEditable(false);
        nmpnj.setName("nmpnj"); // NOI18N
        FormInput.add(nmpnj);
        nmpnj.setBounds(592, 102, 259, 23);

        btnPenjab.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnPenjab.setMnemonic('2');
        btnPenjab.setToolTipText("ALt+2");
        btnPenjab.setName("btnPenjab"); // NOI18N
        btnPenjab.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjabActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab);
        btnPenjab.setBounds(852, 102, 28, 23);

        AsalRujukan.setName("AsalRujukan"); // NOI18N
        AsalRujukan.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AsalRujukanKeyPressed(evt);
            }
        });
        FormInput.add(AsalRujukan);
        AsalRujukan.setBounds(520, 132, 331, 23);

        jLabel23.setText("Asal Rujukan :");
        jLabel23.setName("jLabel23"); // NOI18N
        FormInput.add(jLabel23);
        jLabel23.setBounds(426, 132, 90, 23);

        btnPenjab1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/190.png"))); // NOI18N
        btnPenjab1.setMnemonic('2');
        btnPenjab1.setToolTipText("ALt+2");
        btnPenjab1.setName("btnPenjab1"); // NOI18N
        btnPenjab1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPenjab1ActionPerformed(evt);
            }
        });
        FormInput.add(btnPenjab1);
        btnPenjab1.setBounds(852, 132, 28, 23);

        ChkTracker.setBorder(null);
        ChkTracker.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        ChkTracker.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ChkTracker.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ChkTracker.setName("ChkTracker"); // NOI18N
        FormInput.add(ChkTracker);
        ChkTracker.setBounds(203, 12, 23, 23);

        PanelInput.add(FormInput, java.awt.BorderLayout.CENTER);

        ChkInput.setIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/143.png"))); // NOI18N
        ChkInput.setMnemonic('I');
        ChkInput.setText(".: Input Data");
        ChkInput.setToolTipText("Alt+I");
        ChkInput.setBorderPainted(true);
        ChkInput.setBorderPaintedFlat(true);
        ChkInput.setFocusable(false);
        ChkInput.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        ChkInput.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        ChkInput.setName("ChkInput"); // NOI18N
        ChkInput.setPreferredSize(new java.awt.Dimension(192, 20));
        ChkInput.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/143.png"))); // NOI18N
        ChkInput.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/145.png"))); // NOI18N
        ChkInput.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/icons/145.png"))); // NOI18N
        ChkInput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ChkInputActionPerformed(evt);
            }
        });
        PanelInput.add(ChkInput, java.awt.BorderLayout.PAGE_END);

        internalFrame1.add(PanelInput, java.awt.BorderLayout.PAGE_START);

        getContentPane().add(internalFrame1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void TNoRwKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRwKeyPressed
        Valid.pindah(evt,TNoReg,DTPReg);
}//GEN-LAST:event_TNoRwKeyPressed

    private void DTPRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DTPRegKeyPressed
        Valid.pindah(evt,TNoRw,CmbJam);
}//GEN-LAST:event_DTPRegKeyPressed

    private void TPngJwbKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPngJwbKeyPressed
        Valid.pindah(evt,TNoRM,THbngn);
}//GEN-LAST:event_TPngJwbKeyPressed

    private void TNoRMKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRMKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isPas();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnPasienActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_DOWN){
            if(!TPasien.getText().isEmpty()){
                Map<String, Object> data = new HashMap<>();
                data.put("poli","IGD");
                data.put("antrian",TNoReg.getText());
                data.put("nama",TPasien.getText());
                data.put("norm",TNoRM.getText());
                data.put("bayar",nmpnj.getText());
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting"));
                Valid.MyReport("rptCheckList.jrxml","report","::[ Check List ]::",
                        "select current_date() as sekarang",data); 
            }             
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            isPas();
            TPngJwb.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kddokter.requestFocus();
        }
}//GEN-LAST:event_TNoRMKeyPressed

    private void TNoRegKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TNoRegKeyPressed
        Valid.pindah(evt,TCari,TNoRw);
}//GEN-LAST:event_TNoRegKeyPressed

    private void CmbJamKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbJamKeyPressed
        Valid.pindah(evt,DTPReg,CmbMenit);
}//GEN-LAST:event_CmbJamKeyPressed

    private void CmbMenitKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbMenitKeyPressed
        Valid.pindah(evt,CmbJam,CmbDetik);
}//GEN-LAST:event_CmbMenitKeyPressed

    private void CmbDetikKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CmbDetikKeyPressed
        Valid.pindah(evt,CmbMenit,kddokter);
}//GEN-LAST:event_CmbDetikKeyPressed

    private void TAlmtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TAlmtKeyPressed
        Valid.pindah(evt,THbngn,kdpnj);
}//GEN-LAST:event_TAlmtKeyPressed

    private void BtnPasienActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPasienActionPerformed
        var.setform("DlgIGD");
        pasien.emptTeks();
        pasien.isCek();
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
}//GEN-LAST:event_BtnPasienActionPerformed

    private void THbngnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_THbngnKeyPressed
        Valid.pindah(evt,TPngJwb,TAlmt);
}//GEN-LAST:event_THbngnKeyPressed

    private void ChkJlnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkJlnActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_ChkJlnActionPerformed

    private void BtnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSimpanActionPerformed
       if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else if(kdpnj.getText().trim().equals("")||nmpnj.getText().trim().equals("")){
            Valid.textKosong(kdpnj,"Jenis Bayar");
        }else if(Sequel.cariInteger("select count(pasien.no_rkm_medis) from pasien inner join reg_periksa inner join kamar_inap "+
                 "on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.no_rawat=kamar_inap.no_rawat "+
                 "where kamar_inap.stts_pulang='-' and pasien.no_rkm_medis=?",TNoRM.getText())>0){
            JOptionPane.showMessageDialog(null,"Pasien sedang dalam masa perawatan di kamar inap..!!");
            TNoRM.requestFocus();
        }else{
            ceksukses=false;
            switch (TStatus.getText()) {
                case "Baru":
                        biaya=biayabaru;
                        break;
                case "Lama":
                        biaya=biayalama;
                        break;
                default:
                        biaya=biayabaru;
                        break;
            }
            if(kdigd.equals("")){
                Sequel.menyimpan2("poliklinik","?,?,?,?",4,new String[]{"IGDK","Unit IGD","0","0"});
            }
                
            if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and kd_poli='IGDK'",TNoRM.getText())>0){
                status="Lama";
            }
            if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                    kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                    TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                ceksukses=true;               
            }else{
                Kd2.setText("");
                isNumber();
                if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                        new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                        kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                        TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                    ceksukses=true;           
                }else{
                    Kd2.setText("");
                    isNumber();
                    if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                            new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                            kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                            TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                        ceksukses=true;              
                    }else{
                        Kd2.setText("");
                        isNumber();
                        if(Sequel.menyimpantf2("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                                TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                            ceksukses=true;                
                        }else{
                            Kd2.setText("");
                            isNumber();
                            if(Sequel.menyimpantf("reg_periksa","?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?","No.Rawat",19,
                                    new String[]{TNoReg.getText(),TNoRw.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                    kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),THbngn.getText(),biaya+"","Belum",
                                    TStatus.getText(),"Ralan",kdpnj.getText(),umur,sttsumur,"Belum Bayar",status})==true){
                                ceksukses=true;               
                            }else{
                                Kd2.setText("");
                                TNoRM.requestFocus();
                                isNumber();
                            } 
                        } 
                    } 
                }                
            } 
            if(ceksukses==true){
                UpdateUmur(); 
                if(!AsalRujukan.getText().equals("")){
                    Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(rujuk_masuk.no_rawat,4),signed)),0) from reg_periksa inner join rujuk_masuk on reg_periksa.no_rawat=rujuk_masuk.no_rawat where reg_periksa.tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"' ","BR/"+dateformat.format(DTPReg.getDate())+"/",4,NoBalasan);

                    if(nosisrute.equals("")){
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+AsalRujukan.getText()+"','"+alamatperujuk+"','-','0','"+AsalRujukan.getText()+"','-','-','-','"+NoBalasan.getText()+"'","No.Rujuk");
                    }else{
                        Sequel.menyimpan2("rujuk_masuk","'"+TNoRw.getText()+"','"+AsalRujukan.getText()+"','"+alamatperujuk+"','"+nosisrute+"','0','"+AsalRujukan.getText()+"','-','-','-','"+NoBalasan.getText()+"'","No.Rujuk"); 
                        nosisrute="";
                    }                    
                }
                if(ChkTracker.isSelected()==true){
                    ctk();
                }
                emptTeks(); 
                tampil();
            }
            
        }
}//GEN-LAST:event_BtnSimpanActionPerformed

    private void BtnSimpanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnSimpanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnSimpanActionPerformed(null);
        }else{
            Valid.pindah(evt,kdpnj,BtnBatal);
        }
}//GEN-LAST:event_BtnSimpanKeyPressed

    private void BtnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnBatalActionPerformed
        ChkInput.setSelected(true);
        isForm(); 
        emptTeks();        
}//GEN-LAST:event_BtnBatalActionPerformed

    private void BtnBatalKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnBatalKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            emptTeks();
        }else{Valid.pindah(evt, BtnSimpan, BtnHapus);}
}//GEN-LAST:event_BtnBatalKeyPressed

    private void BtnHapusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnHapusActionPerformed
        for(i=0;i<tbPetugas.getRowCount();i++){ 
            if(tbPetugas.getValueAt(i,0).toString().equals("true")){
                Sequel.meghapus("reg_periksa","no_rawat",tbPetugas.getValueAt(i,2).toString());
            }
        } 
        tampil();
        emptTeks();
}//GEN-LAST:event_BtnHapusActionPerformed

    private void BtnHapusKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnHapusKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnHapusActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnBatal, BtnEdit);
        }
}//GEN-LAST:event_BtnHapusKeyPressed

    private void BtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrintActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){
            Map<String, Object> param = new HashMap<>();                 
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());   
            param.put("logo",Sequel.cariGambar("select logo from setting")); 
               Valid.MyReport("rptReg.jrxml","report","::[ Data Registrasi Periksa ]::","select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,ifnull((select perujuk from rujuk_masuk where rujuk_masuk.no_rawat=reg_periksa.no_rawat),'') as perujuk,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_tlp "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab  "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.kd_pj=penjab.kd_pj "+
                   "and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_poli=poliklinik.kd_poli "+
                   "where poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_reg like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rawat like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.tgl_registrasi like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.kd_dokter like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and dokter.nm_dokter like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.no_rkm_medis like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.stts_daftar like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and pasien.nm_pasien like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and poliklinik.nm_poli like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.p_jawab like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.almt_pj like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and penjab.png_jawab like '%"+TCari.getText().trim()+"%' or "+
                   " poliklinik.kd_poli='IGDK' and tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' and reg_periksa.hubunganpj like '%"+TCari.getText().trim()+"%' order by reg_periksa.no_rawat desc",param);   
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrintActionPerformed

    private void BtnPrintKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrintKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrintActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnAll);
        }
}//GEN-LAST:event_BtnPrintKeyPressed

    private void BtnKeluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluarActionPerformed
        pasien.dispose();
        pasien.bahasa.dispose();
        pasien.cacat.dispose();
        pasien.kab.dispose();
        pasien.kec.dispose();
        pasien.kel.dispose();
        pasien.penjab.dispose();
        pasien.perusahaan.dispose();
        pasien.prop.dispose();
        pasien.suku.dispose();
        dokter.dispose();
        dokter.dokter.dispose();
        dlgrjk.dispose();
        rujukmasuk.WindowPerujuk.dispose();   
        DlgSakit.dispose();
        DlgSakit2.dispose();
        DlgDemografi.dispose();
        DlgCatatan.dispose();
        //var.setAktif(false);
        dispose();
}//GEN-LAST:event_BtnKeluarActionPerformed

    private void BtnKeluarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluarKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluarActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint,TCari);}
}//GEN-LAST:event_BtnKeluarKeyPressed

    private void BtnAllActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAllActionPerformed
        TCari.setText("");
        tampil();
}//GEN-LAST:event_BtnAllActionPerformed

    private void BtnAllKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnAllKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            tampil();
            TCari.setText("");
        }else{
            Valid.pindah(evt, BtnPrint, BtnKeluar);
        }
}//GEN-LAST:event_BtnAllKeyPressed

    private void BtnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditActionPerformed
        if(TNoReg.getText().trim().equals("")){
            Valid.textKosong(TNoReg,"No.Regristrasi");
        }else if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else if(TDokter.getText().trim().equals("")){
            Valid.textKosong(kddokter,"dokter");
        }else if(TNoRM.getText().trim().equals("")||TPasien.getText().trim().equals("")){
            Valid.textKosong(TNoRM,"pasien");
        }else{
            if(tbPetugas.getSelectedRow()>-1){
                switch (TStatus.getText()) {
                    case "Baru":
                            biaya=Sequel.cariIsiAngka("select registrasi from poliklinik where kd_poli='IGDK'");
                            break;
                    case "Lama":
                            biaya=Sequel.cariIsiAngka("select registrasilama from poliklinik where kd_poli='IGDK'");
                            break;
                    default:
                            biaya=Sequel.cariIsiAngka("select registrasi from poliklinik where kd_poli='IGDK'");
                            break;
                }
                if(var.getmanajemen()==true){
                    Sequel.queryu2("update reg_periksa set no_rawat=?,no_reg=?,tgl_registrasi=?,jam_reg=?,kd_dokter=?,no_rkm_medis=?,kd_poli=?,"+
                            "p_jawab=?,almt_pj=?,biaya_reg=?,hubunganpj=?,stts_daftar=?,kd_pj=?,umurdaftar=?,sttsumur=? where no_rawat=?",16,
                            new String[]{TNoRw.getText(),TNoReg.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),""+biaya,THbngn.getText(),
                                TStatus.getText(),kdpnj.getText(),umur,sttsumur,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString()
                        });
                }else{
                    if((Sequel.cariInteger("select count(no_rawat) from rawat_jl_dr where no_rawat=?",TNoRw.getText())>0)||
                            (Sequel.cariInteger("select count(no_rawat) from rawat_jl_pr where no_rawat=?",TNoRw.getText())>0)||
                            (Sequel.cariInteger("select count(no_rawat) from rawat_jl_drpr where no_rawat=?",TNoRw.getText())>0)||
                            (Sequel.cariInteger("select count(no_rawat) from periksa_lab where no_rawat=?",TNoRw.getText())>0)||
                            (Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0)){
                        JOptionPane.showMessageDialog(rootPane,"Maaf pasien sudah ada transaksi sebelumnya & tidak bisa diedit..!!! ");
                        TCari.requestFocus();
                    }else{
                        Sequel.queryu2("update reg_periksa set no_rawat=?,no_reg=?,tgl_registrasi=?,jam_reg=?,kd_dokter=?,no_rkm_medis=?,kd_poli=?,"+
                            "p_jawab=?,almt_pj=?,biaya_reg=?,hubunganpj=?,stts_daftar=?,kd_pj=?,umurdaftar=?,sttsumur=? where no_rawat=?",16,
                            new String[]{TNoRw.getText(),TNoReg.getText(),Valid.SetTgl(DTPReg.getSelectedItem()+""),CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem(),
                                kddokter.getText(),TNoRM.getText(),"IGDK",TPngJwb.getText(),TAlmt.getText(),""+biaya,THbngn.getText(),
                                TStatus.getText(),kdpnj.getText(),umur,sttsumur,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString()
                        });
                    }
                }
                tampil();
                emptTeks();
            }                
        }
}//GEN-LAST:event_BtnEditActionPerformed

    private void BtnEditKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnEditKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnEditActionPerformed(null);
        }else{
            Valid.pindah(evt, BtnHapus, BtnPrint);
        }
}//GEN-LAST:event_BtnEditKeyPressed

    private void TCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TCariKeyPressed
         if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnCariActionPerformed(null);
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            BtnCari.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            BtnKeluar.requestFocus();
        }
}//GEN-LAST:event_TCariKeyPressed

    private void BtnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCariActionPerformed
        tampil();
}//GEN-LAST:event_BtnCariActionPerformed

    private void BtnCariKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnCariKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnCariActionPerformed(null);
        }else{
            Valid.pindah(evt, TCari, BtnAll);
        }
}//GEN-LAST:event_BtnCariKeyPressed

    private void tbPetugasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPetugasMouseClicked
        if(tabMode.getRowCount()!=0){
            try {
                getData();
            } catch (java.lang.NullPointerException e) {
            }
            if(evt.getClickCount()==1){
                i=tbPetugas.getSelectedColumn();
                if(i==8){
                    if(validasicatatan.equals("Yes")){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        LabelCatatan.setText(Sequel.cariIsi("select catatan from catatan_pasien where no_rkm_medis=?",TNoRM.getText()));
                        if(!LabelCatatan.getText().equals("")){
                            DlgCatatan.setLocationRelativeTo(Scroll);
                            DlgCatatan.setVisible(true);
                        }else{
                            DlgCatatan.setVisible(false);
                        }                            
                        this.setCursor(Cursor.getDefaultCursor());
                    }  
                }                
            }else if(evt.getClickCount()==2){
                i=tbPetugas.getSelectedColumn();
//                if(i==1){
//                    if(MnKamarInap.isEnabled()==true){
//                        MnKamarInapActionPerformed(null);
//                    }                    
//                }else 
                if(i==2){
                    if(var.getmanajemen()==true){
                        MnRawatJalanActionPerformed(null);
                    }                    
                }else if(i==3){
                    if(var.getmanajemen()==true){
                        MnPemberianObatActionPerformed(null);
                    }                   
                }else if(i==4){
                    if(var.getmanajemen()==true){
                        MnPeriksaLabActionPerformed(null);
                    }                    
                }else if(i==5){
                    if(var.getmanajemen()==true){
                        MnRujukMasukActionPerformed(null);
                    }                    
                }
            }
            
        }
        
}//GEN-LAST:event_tbPetugasMouseClicked

    private void tbPetugasKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyPressed
        if(tabMode.getRowCount()!=0){
            if(evt.getKeyCode()==KeyEvent.VK_SPACE){
                i=tbPetugas.getSelectedColumn();
//                if(i==1){
//                    if(MnKamarInap.isEnabled()==true){
//                        MnKamarInapActionPerformed(null);
//                    }                    
//                }else 
                if(i==2){
                    if(var.getmanajemen()==true){
                        MnRawatJalanActionPerformed(null);
                    }                    
                }else if(i==3){
                    if(var.getmanajemen()==true){
                        MnPemberianObatActionPerformed(null);
                    }                   
                }else if(i==4){
                    if(var.getmanajemen()==true){
                        MnPeriksaLabActionPerformed(null);
                    }                    
                }else if(i==5){
                    if(var.getmanajemen()==true){
                        MnRujukMasukActionPerformed(null);
                    }                    
                }
            }else if(evt.getKeyCode()==KeyEvent.VK_L){
                MnBarcodeRM9ActionPerformed(null);
            }else if(evt.getKeyCode()==KeyEvent.VK_Z){
               // MnSPBK1ActionPerformed(null);
            }else if(evt.getKeyCode()==KeyEvent.VK_W){
                MnLembarRalanActionPerformed(null);
            }
        }
}//GEN-LAST:event_tbPetugasKeyPressed

private void kddokterKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kddokterKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            isNumber();
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            BtnDokterActionPerformed(null);
        }else{
            isNumber();
            Sequel.cariIsi("select nm_dokter from dokter where kd_dokter=?",TDokter,kddokter.getText());
            Valid.pindah(evt,CmbDetik,TNoRM);
        }
}//GEN-LAST:event_kddokterKeyPressed

private void BtnDokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnDokterActionPerformed
        var.setform("DlgIGD");
        pilihan=1;
        dokter.isCek();        
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
}//GEN-LAST:event_BtnDokterActionPerformed

private void MnRawatJalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRawatJalanActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                            JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    DlgRawatJalan dlgrwjl=new DlgRawatJalan(null,false);
                    dlgrwjl.isCek();
                    dlgrwjl.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                    dlgrwjl.setLocationRelativeTo(internalFrame1);
                    dlgrwjl.SetPoli("IGDK");
                    dlgrwjl.SetPj(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
                    dlgrwjl.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate());
                    dlgrwjl.setVisible(true);
                }
            }
                                
        }
}//GEN-LAST:event_MnRawatJalanActionPerformed

private void MnRujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{            
            DlgRujuk dlgrjk=new DlgRujuk(null,false);
            dlgrjk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.emptTeks();
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
            dlgrjk.tampil();
            dlgrjk.setVisible(true);
        }
}//GEN-LAST:event_MnRujukActionPerformed

private void MnPemberianObatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPemberianObatActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPemberianObat dlgrwinap=new DlgPemberianObat(null,false);
                dlgrwinap.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgrwinap.setLocationRelativeTo(internalFrame1);
                dlgrwinap.isCek();
                dlgrwinap.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"ralan");
                dlgrwinap.tampilPO();
                dlgrwinap.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnPemberianObatActionPerformed

private void MnBillingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
                }else {
                    try {
                        pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                        try {
                            pscaripiutang.setString(1,TNoRM.getText());
                            rs=pscaripiutang.executeQuery();
                            if(rs.next()){
                                i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                                if(i==JOptionPane.YES_OPTION){
                                     DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                     piutang.setNoRm(TNoRM.getText(),rs.getDate(1));
                                     piutang.tampil();
                                     piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                     piutang.setLocationRelativeTo(internalFrame1);
                                     piutang.setVisible(true);
                                }else{                                
                                    DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
                                    dlgbil.TNoRw.setText(TNoRw.getText());  
                                    dlgbil.isCek();
                                    dlgbil.isRawat(); 
                                    dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                                    dlgbil.setLocationRelativeTo(internalFrame1);
                                    dlgbil.setVisible(true);
                                }
                            }else{                            
                                DlgBilingRalan dlgbil=new DlgBilingRalan(null,false);
                                dlgbil.TNoRw.setText(TNoRw.getText());  
                                dlgbil.isCek();
                                dlgbil.isRawat(); 
                                dlgbil.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                                dlgbil.setLocationRelativeTo(internalFrame1);
                                dlgbil.setVisible(true);
                            }
                        } catch (Exception e) {
                            System.out.println("Notif : "+e);
                        } finally{
                            if(rs!=null){
                                rs.close();
                            }
                            if(pscaripiutang!=null){
                                pscaripiutang.close();
                            }
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }                            
                }                    
            }              
        }
}//GEN-LAST:event_MnBillingActionPerformed

private void ChkInputActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ChkInputActionPerformed
  isForm();                
}//GEN-LAST:event_ChkInputActionPerformed

private void ppGrafikPerpoliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerpoliActionPerformed
       tampil();
}//GEN-LAST:event_ppGrafikPerpoliActionPerformed

private void ppGrafikPerdokterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerdokterActionPerformed
      tampil();
}//GEN-LAST:event_ppGrafikPerdokterActionPerformed

private void ppGrafikPerJKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerJKActionPerformed
      tampil();
}//GEN-LAST:event_ppGrafikPerJKActionPerformed

private void ppGrafikPerPekerjaanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerPekerjaanActionPerformed
       tampil();
}//GEN-LAST:event_ppGrafikPerPekerjaanActionPerformed

private void ppGrafikPerAgamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerAgamaActionPerformed
     tampil();
}//GEN-LAST:event_ppGrafikPerAgamaActionPerformed

private void ppGrafikPerTahunActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTahunActionPerformed
      tampil();
}//GEN-LAST:event_ppGrafikPerTahunActionPerformed

private void ppGrafikPerBulanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerBulanActionPerformed
      tampil();
}//GEN-LAST:event_ppGrafikPerBulanActionPerformed

private void ppGrafikPerTanggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikPerTanggalActionPerformed
      tampil();
}//GEN-LAST:event_ppGrafikPerTanggalActionPerformed

private void MnPeriksaLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaLabActionPerformed
       if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaLaboratorium dlgro=new DlgPeriksaLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                dlgro.setVisible(true);
            }                
        }
}//GEN-LAST:event_MnPeriksaLabActionPerformed

private void MnCetakSuratSakitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakSuratSakitActionPerformed
     DlgSakit.setSize(550,121);
     DlgSakit.setLocationRelativeTo(internalFrame1);
     DlgSakit.setVisible(true);
}//GEN-LAST:event_MnCetakSuratSakitActionPerformed

private void BtnPrint2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint2ActionPerformed
      if(TPasien.getText().trim().equals("")){
          JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");                
      }else{
          Map<String, Object> param = new HashMap<>();
            param.put("hari",lmsakit.getText());
            param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
            param.put("namars",var.getnamars());
                param.put("alamatrs",var.getalamatrs());
                param.put("kotars",var.getkabupatenrs());
                param.put("propinsirs",var.getpropinsirs());
                param.put("kontakrs",var.getkontakrs());
                param.put("emailrs",var.getemailrs());   
                param.put("logo",Sequel.cariGambar("select logo from setting")); 
          Valid.MyReport("rptSuratSakit.jrxml","report","::[ Surat Sakit ]::",
                        "select reg_periksa.no_rawat,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                        " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat" +
                        " from reg_periksa inner join pasien inner join dokter" +
                        " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter "+
                        "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
      }
}//GEN-LAST:event_BtnPrint2ActionPerformed

private void BtnKeluar2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar2ActionPerformed
   DlgSakit.dispose();
}//GEN-LAST:event_BtnKeluar2ActionPerformed

private void BtnPrint3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint3ActionPerformed
                
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
            } 
}//GEN-LAST:event_BtnPrint3ActionPerformed

private void BtnKeluar3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar3ActionPerformed
   DlgDemografi.dispose();
}//GEN-LAST:event_BtnKeluar3ActionPerformed

private void btnKelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKelActionPerformed
        var.setform("DlgIGD");
        pasien.kel.emptTeks();
        pasien.kel.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kel.setLocationRelativeTo(internalFrame1);
        pasien.kel.setVisible(true);
     
}//GEN-LAST:event_btnKelActionPerformed

private void btnKecActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKecActionPerformed
   var.setform("DlgIGD");
   pasien.kec.emptTeks();
        pasien.kec.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kec.setLocationRelativeTo(internalFrame1);
        pasien.kec.setVisible(true);      
}//GEN-LAST:event_btnKecActionPerformed

private void btnKabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnKabActionPerformed
        var.setform("DlgIGD");
        pasien.kab.emptTeks();
        pasien.kab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.kab.setLocationRelativeTo(internalFrame1);
        pasien.kab.setVisible(true);   
}//GEN-LAST:event_btnKabActionPerformed

private void BtnPrint4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint4ActionPerformed
        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data sudah habis. Tidak ada data yang bisa anda print...!!!!");
            BtnBatal.requestFocus();
        }else if(tabMode.getRowCount()!=0){            
            if(!Kelurahan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Area");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Area Kelurahan "+Kelurahan2.getText()+", Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select  pasien.alamat as area,count(pasien.alamat) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' "+
                   "and kelurahan.nm_kel='"+Kelurahan2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by pasien.alamat order by pasien.alamat",data);
            }else if(!Kecamatan2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+", Kabupaten "+Kabupaten2.getText());
                data.put("area","Kelurahan");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kelurahan Kecamatan "+Kecamatan2.getText()+" Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kelurahan.nm_kel as area,count(kelurahan.nm_kel) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan inner join kelurahan on pasien.kd_kab=kabupaten.kd_kab "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kel=kelurahan.kd_kel and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and kecamatan.nm_kec='"+Kecamatan2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kelurahan.nm_kel order by kelurahan.nm_kel",data);
            }else if(!Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kecamatan Kabupaten "+Kabupaten2.getText());
                data.put("area","Kecamatan");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Per Kecamatan Kabupaten "+Kabupaten2.getText()+" ]::",
                   "select kecamatan.nm_kec as area,count(kecamatan.nm_kec) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten inner join kecamatan on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and pasien.kd_kec=kecamatan.kd_kec where kabupaten.nm_kab='"+Kabupaten2.getText()+"' and reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kecamatan.nm_kec order by kecamatan.nm_kec",data);
            }else if(Kabupaten2.getText().equals("")){
                DlgDemografi.dispose();   
                Map<String, Object> data = new HashMap<>();
                data.put("judul","Data Demografi Per Kabupaten");
                data.put("area","Kabupaten");
                data.put("namars",var.getnamars());
                data.put("alamatrs",var.getalamatrs());
                data.put("kotars",var.getkabupatenrs());
                data.put("propinsirs",var.getpropinsirs());
                data.put("kontakrs",var.getkontakrs());
                data.put("emailrs",var.getemailrs());
                data.put("logo",Sequel.cariGambar("select logo from setting")); 
                Valid.MyReport("rptDemografi.jrxml","report","::[ Data Demografi Per Kabupaten ]::","select kabupaten.nm_kab as area,count(kabupaten.nm_kab) as jumlah from reg_periksa inner join pasien "+
                   "inner join kabupaten on pasien.kd_kab=kabupaten.kd_kab and reg_periksa.no_rkm_medis=pasien.no_rkm_medis where reg_periksa.tgl_registrasi between '"+Valid.SetTgl(DTPCari1.getSelectedItem()+"")+"' and '"+Valid.SetTgl(DTPCari2.getSelectedItem()+"")+"' group by kabupaten.nm_kab order by kabupaten.nm_kab",data);
            }                        
        }
        this.setCursor(Cursor.getDefaultCursor());
}//GEN-LAST:event_BtnPrint4ActionPerformed

private void ppGrafikDemografiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppGrafikDemografiActionPerformed
        DlgDemografi.setSize(550,180);
        DlgDemografi.setLocationRelativeTo(internalFrame1);
        DlgDemografi.setVisible(true);
}//GEN-LAST:event_ppGrafikDemografiActionPerformed

private void MnRujukMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukMasukActionPerformed
   if(TPasien.getText().trim().equals("")){
                JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
                tbPetugas.requestFocus();
      }else{
            
                DlgRujukMasuk rujukmasuk=new DlgRujukMasuk(null,false);
                rujukmasuk.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                rujukmasuk.setLocationRelativeTo(internalFrame1);
                rujukmasuk.emptTeks();
                rujukmasuk.isCek();
                rujukmasuk.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate()); 
                rujukmasuk.tampil();
                rujukmasuk.setVisible(true);
                //this.dispose();
        }
}//GEN-LAST:event_MnRujukMasukActionPerformed

private void kdpnjKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_kdpnjKeyPressed
       if(evt.getKeyCode()==KeyEvent.VK_PAGE_DOWN){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());      
        }else if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            TAlmt.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            Sequel.cariIsi("select png_jawab from penjab where kd_pj=?",nmpnj,kdpnj.getText());
            AsalRujukan.requestFocus();      
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }
}//GEN-LAST:event_kdpnjKeyPressed

private void btnPenjabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjabActionPerformed
        var.setform("DlgIGD");
        pasien.penjab.onCari();
        pasien.penjab.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.penjab.setLocationRelativeTo(internalFrame1);
        pasien.penjab.setVisible(true);
}//GEN-LAST:event_btnPenjabActionPerformed

    private void MnNoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnNoResepActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                        JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgResepObat resep=new DlgResepObat(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.emptTeks();
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),CmbJam.getSelectedItem().toString(),CmbMenit.getSelectedItem().toString(),CmbDetik.getSelectedItem().toString(),"ralan");
                resep.tampil();
                resep.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnNoResepActionPerformed

    private void Kabupaten2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kabupaten2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKabActionPerformed(null);
        }else{Valid.pindah(evt,BtnKeluar3,Kecamatan2);}
    }//GEN-LAST:event_Kabupaten2KeyPressed

    private void Kecamatan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kecamatan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKecActionPerformed(null);
        }else{Valid.pindah(evt,Kabupaten2,Kelurahan2);}
    }//GEN-LAST:event_Kecamatan2KeyPressed

    private void Kelurahan2KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Kelurahan2KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnKelActionPerformed(null);
        }else{Valid.pindah(evt,Kecamatan2,BtnPrint4);}
    }//GEN-LAST:event_Kelurahan2KeyPressed

    private void BtnPrint4KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint4KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrint4ActionPerformed(null);
        }else{Valid.pindah(evt,Kelurahan2,BtnPrint3);}
    }//GEN-LAST:event_BtnPrint4KeyPressed

    private void BtnPrint3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnPrint3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnPrint3ActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint4,BtnKeluar3);}
    }//GEN-LAST:event_BtnPrint3KeyPressed

    private void BtnKeluar3KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar3KeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_SPACE){
            BtnKeluar3ActionPerformed(null);
        }else{Valid.pindah(evt,BtnPrint3,Kabupaten2);}
    }//GEN-LAST:event_BtnKeluar3KeyPressed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        tampil();
    }//GEN-LAST:event_formWindowOpened

    private void MnPeriksaRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPeriksaRadiologiActionPerformed
        if(tbPetugas.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeriksaRadiologi dlgro=new DlgPeriksaRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");  
                dlgro.tampil();
                dlgro.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnPeriksaRadiologiActionPerformed

    private void MnDiagnosaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDiagnosaActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Masukkan diagnosa lewat kamar inap..!!!");
            }else {
                DlgDiagnosaPenyakit resep=new DlgDiagnosaPenyakit(null,false);
                resep.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
                resep.setLocationRelativeTo(internalFrame1);
                resep.isCek();
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Ralan");
                resep.panelDiagnosa1.tampil();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnDiagnosaActionPerformed

    private void AsalRujukanKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AsalRujukanKeyPressed
        if(evt.getKeyCode()==KeyEvent.VK_PAGE_UP){
            kdpnj.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_ENTER){
            BtnSimpan.requestFocus();
        }else if(evt.getKeyCode()==KeyEvent.VK_UP){
            btnPenjabActionPerformed(null);
        }
    }//GEN-LAST:event_AsalRujukanKeyPressed

    private void btnPenjab1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPenjab1ActionPerformed
        var.setform("DlgReg");
        rujukmasuk.tampil2();
        rujukmasuk.TCariPerujuk.requestFocus();
        rujukmasuk.WindowPerujuk.setSize(this.getWidth()-20,this.getHeight()-20);
        rujukmasuk.WindowPerujuk.setLocationRelativeTo(internalFrame1);
        rujukmasuk.WindowPerujuk.setVisible(true);
    }//GEN-LAST:event_btnPenjab1ActionPerformed

    private void MnCetakRegisterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnCetakRegisterActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
             this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBuktiRegister.jrxml","report","::[ Bukti Register ]::",
                   "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(year(from_days(datediff(now(), pasien.tgl_lahir))),' Th ',month(from_days(datediff(now(),pasien.tgl_lahir))),' Bl ',day(from_days(datediff(now(),pasien.tgl_lahir))),' Hr')as umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnCetakRegisterActionPerformed

    private void MnPersetujuanMedisActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPersetujuanMedisActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("persetujuantindakanmedis.jrxml","report","::[ Persetujuan Tindakan ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                   "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                   "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                   "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                   "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                   "inner join kelurahan inner join kecamatan inner join kabupaten "+
                   "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                   "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPersetujuanMedisActionPerformed

    private void BtnPrint5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPrint5ActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("hari",lmsakit.getText());
            param.put("TanggalAwal",TglSakit1.getSelectedItem().toString());
            param.put("TanggalAkhir",TglSakit2.getSelectedItem().toString());
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("nomersurat",NomorSurat.getText());
            param.put("dokterpj",CrDokter3.getText());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSuratSakit2.jrxml","report","::[ Surat Sakit ]::",
                "select reg_periksa.no_rkm_medis,dokter.nm_dokter,pasien.keluarga,pasien.namakeluarga,pasien.tgl_lahir,pasien.jk," +
                " pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,pasien.pekerjaan,pasien.alamat" +
                " from reg_periksa inner join pasien inner join dokter" +
                " on reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_dokter=dokter.kd_dokter  "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_BtnPrint5ActionPerformed

    private void BtnKeluar4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar4ActionPerformed
        DlgSakit2.dispose();
    }//GEN-LAST:event_BtnKeluar4ActionPerformed

    private void BtnSeek5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnSeek5ActionPerformed
        var.setform("DlgReg");
        pilihan=3;
        dokter.isCek();
        dokter.TCari.requestFocus();
        dokter.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        dokter.setLocationRelativeTo(internalFrame1);
        dokter.setVisible(true);
    }//GEN-LAST:event_BtnSeek5ActionPerformed

    private void MnOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            DlgTagihanOperasi dlgro=new DlgTagihanOperasi(null,false);
            dlgro.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgro.setLocationRelativeTo(internalFrame1);
            dlgro.setNoRm(TNoRw.getText(),TNoRM.getText()+", "+TPasien.getText(),"Ralan");
            dlgro.setVisible(true);
        }
    }//GEN-LAST:event_MnOperasiActionPerformed

    private void MnGelang1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnGelang1ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TCari.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("tanggal",DTPReg.getSelectedItem().toString());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            param.put("dokter",Sequel.cariIsi("select dokter.nm_dokter from dokter, dpjp_ranap where dokter.kd_dokter=dpjp_ranap.kd_dokter and dpjp_ranap.no_rawat=?",TNoRw.getText()));
            
            Valid.MyReport("rptBarcodeRM6.jrxml","report","::[ Gelang Pasien ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnGelang1ActionPerformed

    private void MnSEPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSEPActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgIGD");
            BPJSDataSEP dlgki=new BPJSDataSEP(null,false);
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText(),DTPReg.getDate(),"2. Ralan","IGDK","Unit IGD/UGD");
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSEPActionPerformed

    private void MnLembarCasemixActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarCasemixActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLembarCasemix.jrxml","report","::[ Surat Jaminan & Bukti Pelayanan Rawat Jalan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarCasemixActionPerformed

    private void ppCatatanPasienBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppCatatanPasienBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            catatan.setNoRm(TNoRM.getText());
            catatan.setSize(720,330);
            catatan.setLocationRelativeTo(internalFrame1);
            catatan.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppCatatanPasienBtnPrintActionPerformed

    private void MnSPBKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSPBKActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptSBPK.jrxml","report","::[ Surat Bukti Pelayanan Kesehatan ]::",
                "select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,"+
                "reg_periksa.jam_reg, reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli," +
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg," +
                "reg_periksa.stts_daftar,penjab.png_jawab,pasien.no_peserta,pasien.tgl_lahir " +
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab " +
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli "+
                "where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnSPBKActionPerformed

    private void ppRiwayatBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppRiwayatBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgResumePerawatan resume=new DlgResumePerawatan(null,true);
            resume.setNoRm(TNoRM.getText(),TPasien.getText());
            resume.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            resume.setLocationRelativeTo(internalFrame1);
            resume.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppRiwayatBtnPrintActionPerformed

    private void MnResepDOkterActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnResepDOkterActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgPeresepanDokter resep=new DlgPeresepanDokter(null,false);
                resep.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                resep.setLocationRelativeTo(internalFrame1);
                resep.setNoRm(TNoRw.getText(),DTPCari1.getDate(),CmbJam.getSelectedItem().toString(),CmbMenit.getSelectedItem().toString(),CmbDetik.getSelectedItem().toString(),
                    kddokter.getText(),TDokter.getText(),"ralan");
                resep.isCek();
                resep.tampilobat();
                resep.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnResepDOkterActionPerformed

    private void ppBerkasBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            //TNoReg.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            Sequel.menyimpan("mutasi_berkas","'"+TNoRw.getText()+"','Sudah Diterima',now(),now(),'0000-00-00 00:00:00','0000-00-00 00:00:00'","status='Sudah Diterima',diterima=now()","no_rawat='"+TNoRw.getText()+"'");
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Berkas Diterima'");
            if(tabMode.getRowCount()!=0){tampil();}
        }
    }//GEN-LAST:event_ppBerkasBtnPrintActionPerformed

    private void MnSudahActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSudahActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Sudah'");
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnSudahActionPerformed

    private void MnBelumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBelumActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Belum'");
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnBelumActionPerformed

    private void MnBatalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBatalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Batal',biaya_reg='0'");
                if(tabMode.getRowCount()!=0){tampil();}
            }
        }
    }//GEN-LAST:event_MnBatalActionPerformed

    private void MnDirujukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDirujukActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Dirujuk'");
                MnRujukActionPerformed(evt);
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnDirujukActionPerformed

    private void MnDIrawatActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDIrawatActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Dirawat'");
                //MnKamarInapActionPerformed(evt);
                kamar_inap();
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnDIrawatActionPerformed

    private void MnMeninggalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnMeninggalActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Meninggal'");
                DlgPasienMati dlgPasienMati=new DlgPasienMati(null,false);
                dlgPasienMati.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgPasienMati.setLocationRelativeTo(internalFrame1);
                dlgPasienMati.emptTeks();
                dlgPasienMati.setNoRm(TNoRM.getText());
                dlgPasienMati.isCek();
                dlgPasienMati.setVisible(true);
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnMeninggalActionPerformed

    private void LabelCatatanMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_LabelCatatanMouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_LabelCatatanMouseClicked

    private void internalFrame6MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_internalFrame6MouseClicked
        DlgCatatan.dispose();
    }//GEN-LAST:event_internalFrame6MouseClicked

    private void MnLabelTrackerActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLabelTrackerActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("no_rawat",TNoRw.getText());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptLabelTracker.jrxml",param,"::[ Label Tracker ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLabelTrackerActionPerformed

    private void MnBarcode1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcode1ActionPerformed
        if(!TPasien.getText().equals("")){
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("nama",TPasien.getText());
            param.put("alamat",Sequel.cariIsi("select date_format(tgl_lahir,'%d/%m/%Y') from pasien where no_rkm_medis=?",TNoRM.getText()));
            param.put("norm",TNoRM.getText());
            param.put("parameter","%"+TCari.getText().trim()+"%");
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            Valid.MyReport("rptBarcodeRawat.jrxml","report","::[ Barcode No.Rawat ]::",
                "select reg_periksa.no_rawat from reg_periksa where no_rawat='"+TNoRw.getText()+"'",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcode1ActionPerformed

    private void MnJKRAActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJKRAActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("perujuk",Sequel.cariIsi("select perujuk from rujuk_masuk where no_rawat=?",TNoRw.getText()));
            param.put("no_rawat",TNoRw.getText());
            param.put("petugas",Sequel.cariIsi("select nama from petugas where nip=?",var.getkode()));
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptJkra.jrxml",param,"::[ SURAT JAMINAN KESEHATAN NASIONAL (JKRA) RAWAT JALAN ]::");
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnJKRAActionPerformed

    private void MnLembarRalanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnLembarRalanActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptRM11.jrxml","report","::[ Lembar Rawat Jalan ]::",
                "SELECT reg_periksa.tgl_registrasi, reg_periksa.jam_reg, "+
                "poliklinik.nm_poli, pasien.no_rkm_medis, pasien.nm_pasien, "+
                "pasien.no_ktp, pasien.jk, pasien.tmp_lahir, pasien.tgl_lahir,"+
                "pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel"+
                ",', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) AS alamat,"+
                "pasien.gol_darah,pasien.pekerjaan,pasien.stts_nikah,"+
                "pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd,pasien.keluarga,pasien.namakeluarga,"+
                "penjab.png_jawab,pasien.pekerjaanpj,concat(pasien.alamatpj,"+
                "', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',"+
                "pasien.kabupatenpj) AS alamatpj FROM pasien INNER JOIN "+
                "kelurahan INNER JOIN kecamatan INNER JOIN kabupaten "+
                "INNER JOIN penjab ON pasien.kd_kel = kelurahan.kd_kel "+
                "AND pasien.kd_kec = kecamatan.kd_kec AND pasien.kd_kab = kabupaten.kd_kab "+
                "INNER JOIN reg_periksa ON reg_periksa.no_rkm_medis = pasien.no_rkm_medis "+
                "AND reg_periksa.kd_pj = penjab.kd_pj INNER JOIN poliklinik "+
                "ON poliklinik.kd_poli = reg_periksa.kd_poli WHERE "+
                "reg_periksa.no_rawat ='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnLembarRalanActionPerformed

    private void MnBarcodeRM9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBarcodeRM9ActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBarcodeRM18.jrxml","report","::[ Label Rekam Medis ]::","select pasien.no_rkm_medis, pasien.nm_pasien, pasien.no_ktp, pasien.jk, "+
                "pasien.tmp_lahir, pasien.tgl_lahir,pasien.nm_ibu, concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) as alamat, pasien.gol_darah, pasien.pekerjaan,"+
                "pasien.stts_nikah,pasien.agama,pasien.tgl_daftar,pasien.no_tlp,pasien.umur,"+
                "pasien.pnd, pasien.keluarga, pasien.namakeluarga,penjab.png_jawab,pasien.pekerjaanpj,"+
                "concat(pasien.alamatpj,', ',pasien.kelurahanpj,', ',pasien.kecamatanpj,', ',pasien.kabupatenpj) as alamatpj from pasien "+
                "inner join kelurahan inner join kecamatan inner join kabupaten "+
                "inner join penjab on pasien.kd_pj=penjab.kd_pj and pasien.kd_kel=kelurahan.kd_kel "+
                "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab  where pasien.no_rkm_medis='"+TNoRM.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBarcodeRM9ActionPerformed

    private void ppBerkasDigitalBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppBerkasDigitalBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgBerkasRawat berkas=new DlgBerkasRawat(null,true);
            berkas.setJudul("::[ Berkas Digital Perawatan ]::","berkasrawat/pages");
            try {
                berkas.loadURL("http://"+prop.getProperty("HOSTHYBRIDWEB")+":"+prop.getProperty("PORTWEB")+"/"+prop.getProperty("HYBRIDWEB")+"/"+"berkasrawat/login2.php?act=login&usere=admin&passwordte=akusayangsamakamu&no_rawat="+TNoRw.getText());
            } catch (Exception ex) {
                System.out.println("Notifikasi : "+ex);
            }

            berkas.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            berkas.setLocationRelativeTo(internalFrame1);
            berkas.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }            
    }//GEN-LAST:event_ppBerkasDigitalBtnPrintActionPerformed

    private void ppIKPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppIKPBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data pasien sudah habis...!!!!");
            TNoRw.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data registrasi pada table...!!!");
            TCari.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            DlgDataInsidenKeselamatan aplikasi=new DlgDataInsidenKeselamatan(null,false);
            aplikasi.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            aplikasi.setLocationRelativeTo(internalFrame1);
            aplikasi.isCek();
            aplikasi.setNoRm(TNoRw.getText(),DTPCari1.getDate(),DTPCari2.getDate(),"Unit Gawat Darurat");
            aplikasi.tampil();
            aplikasi.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_ppIKPBtnPrintActionPerformed

    private void MnPoliInternalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPoliInternalActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            dlgrjk.setLocationRelativeTo(internalFrame1);
            dlgrjk.isCek();
            dlgrjk.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgrjk.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnPoliInternalActionPerformed

    private void MnJadwalOperasiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJadwalOperasiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgBookingOperasi form=new DlgBookingOperasi(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText(),"IGD","Ralan");
                form.setVisible(true);
            }
        }
    }//GEN-LAST:event_MnJadwalOperasiActionPerformed

    private void MnSKDPBPJSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSKDPBPJSActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                DlgSKDPBPJS form=new DlgSKDPBPJS(null,false);
                form.isCek();
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);      
                form.emptTeks();      
                form.setNoRm(TNoRM.getText(),TPasien.getText()); 
                form.setVisible(true);
            }                
        }
    }//GEN-LAST:event_MnSKDPBPJSActionPerformed

    private void MnPermintaanLabActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanLabActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanLaboratorium dlgro=new DlgPermintaanLaboratorium(null,false);
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanLabActionPerformed

    private void MnPermintaanRadiologiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPermintaanRadiologiActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                DlgPermintaanRadiologi dlgro=new DlgPermintaanRadiologi(null,false);
                dlgro.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                dlgro.setLocationRelativeTo(internalFrame1);
                dlgro.emptTeks();
                dlgro.isCek();
                dlgro.setNoRm(TNoRw.getText(),"Ralan");
                dlgro.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnPermintaanRadiologiActionPerformed

    private void MnPulangPaksaBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnPulangPaksaBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"stts='Pulang Paksa'");
                if(tabMode.getRowCount()!=0){tampil();}
            }

        }
    }//GEN-LAST:event_MnPulangPaksaBtnPrintActionPerformed

    private void MnStatusBaruActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusBaruActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"status_poli='Baru'");
            if(tabMode.getRowCount()!=0){tampil();}
        }
    }//GEN-LAST:event_MnStatusBaruActionPerformed

    private void MnStatusLamaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnStatusLamaActionPerformed
        if(TNoRw.getText().trim().equals("")){
            Valid.textKosong(TNoRw,"No.Rawat");
        }else{
            Valid.editTable(tabMode,"reg_periksa","no_rawat",TNoRw,"status_poli='Lama'");
            if(tabMode.getRowCount()!=0){tampil();}
        }
    }//GEN-LAST:event_MnStatusLamaActionPerformed

    private void MnBillingParsialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBillingParsialActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(Sequel.cariInteger("select count(no_rawat) from kamar_inap where no_rawat=?",TNoRw.getText())>0){
                JOptionPane.showMessageDialog(null,"Maaf, Pasien sudah masuk Kamar Inap. Gunakan billing Ranap..!!!");
            }else {
                try {
                    pscaripiutang=koneksi.prepareStatement("select tgl_piutang from piutang_pasien where no_rkm_medis=? and status='Belum Lunas' order by tgl_piutang asc limit 1");
                    try {
                        pscaripiutang.setString(1,TNoRM.getText());
                        rs=pscaripiutang.executeQuery();
                        if(rs.next()){
                            i=JOptionPane.showConfirmDialog(null, "Masih ada tunggakan pembayaran, apa mau bayar sekarang ?","Konfirmasi",JOptionPane.YES_NO_OPTION);
                            if(i==JOptionPane.YES_OPTION){
                                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                                DlgLhtPiutang piutang=new DlgLhtPiutang(null,false);
                                piutang.setNoRm(TNoRM.getText(),rs.getDate(1));
                                piutang.tampil();
                                piutang.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                                piutang.setLocationRelativeTo(internalFrame1);
                                piutang.setVisible(true);
                                this.setCursor(Cursor.getDefaultCursor());
                            }else{
                                billingprasial();
                            }
                        }else{
                            billingprasial();
                        }
                    } catch (Exception e) {
                        System.out.println(e);
                    }finally{
                        if( rs != null){
                            rs.close();
                        }
                        if( pscaripiutang != null){
                            pscaripiutang.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        }

    }//GEN-LAST:event_MnBillingParsialActionPerformed

    private void tbPetugasKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbPetugasKeyReleased
        if(tabMode.getRowCount()!=0){
            if((evt.getKeyCode()==KeyEvent.VK_ENTER)||(evt.getKeyCode()==KeyEvent.VK_UP)||(evt.getKeyCode()==KeyEvent.VK_DOWN)){
                try {
                    getData();
                } catch (java.lang.NullPointerException e) {
                }
            }              
        }
    }//GEN-LAST:event_tbPetugasKeyReleased

    private void MnBlangkoResepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnBlangkoResepActionPerformed
        if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            Map<String, Object> param = new HashMap<>();
            param.put("namars",var.getnamars());
            param.put("alamatrs",var.getalamatrs());
            param.put("kotars",var.getkabupatenrs());
            param.put("propinsirs",var.getpropinsirs());
            param.put("kontakrs",var.getkontakrs());
            param.put("emailrs",var.getemailrs());
            param.put("logo",Sequel.cariGambar("select logo from setting"));
            Valid.MyReport("rptBlankoResep.jrxml","report","::[ Bukti Register ]::",
                "select pasien.no_ktp,pasien.no_peserta,reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,pasien.no_tlp,"+
                "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,pasien.umur as umur,poliklinik.nm_poli,"+
                "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab "+
                "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli where reg_periksa.no_rawat='"+TNoRw.getText()+"' ",param);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnBlangkoResepActionPerformed

    private void MnRujukSisruteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnRujukSisruteActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            var.setform("DlgIGD");
            SisruteRujukanKeluar dlgki=new SisruteRujukanKeluar(null,false);
            dlgki.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.isCek();
            dlgki.setPasien(TNoRw.getText());
            dlgki.setVisible(true);
            this.setCursor(Cursor.getDefaultCursor());
        }
    }//GEN-LAST:event_MnRujukSisruteActionPerformed

    private void MnSuratKontrolVclaimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnSuratKontrolVclaimActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TCari.requestFocus();
        }else if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                try {
                    ps=koneksi.prepareStatement("select * from bridging_sep where no_rawat=?");
                    try {
                        ps.setString(1,TNoRw.getText());
                        rs=ps.executeQuery();
                        if(rs.next()){
                            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                            BPJSSuratKontrol form=new BPJSSuratKontrol(null,false);
                            form.setNoRm(rs.getString("no_rawat"),rs.getString("no_sep"),rs.getString("no_kartu"),rs.getString("nomr"),rs.getString("nama_pasien"),rs.getString("tanggal_lahir"),rs.getString("jkel"),rs.getString("nmdiagnosaawal"));
                            form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                            form.setLocationRelativeTo(internalFrame1);
                            form.setVisible(true);
                            this.setCursor(Cursor.getDefaultCursor());
                        }else{
                            JOptionPane.showMessageDialog(null,"Pasien tersebut belum terbit SEP, silahkan hubungi bagian terkait..!!");
                            TCari.requestFocus();
                        }
                    } catch (Exception e) {
                        System.out.println("Notif : "+e);
                    } finally{
                        if(rs!=null){
                            rs.close();
                        }
                        if(ps!=null){
                            ps.close();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Notif : "+e);
                }
            }
        }
    }//GEN-LAST:event_MnSuratKontrolVclaimActionPerformed

    private void TSaranJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TSaranJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TSaranJawabKeyPressed

    private void TKonsulJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TKonsulJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TKonsulJawabKeyPressed

    private void TPemeriksaanJawabKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TPemeriksaanJawabKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_TPemeriksaanJawabKeyPressed

    private void BtnKeluar6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnKeluar6ActionPerformed
        DlgJawabanKonsul.dispose();
    }//GEN-LAST:event_BtnKeluar6ActionPerformed

    private void BtnKeluar6KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_BtnKeluar6KeyPressed

    }//GEN-LAST:event_BtnKeluar6KeyPressed

    private void MnJawabanKonsulActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnJawabanKonsulActionPerformed
        DlgJawabanKonsul.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        DlgJawabanKonsul.setLocationRelativeTo(internalFrame1);
        //DlgJawabanKonsul.setDataPasien(tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),6).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),7).toString(), tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),4).toString(),Sequel.cariIsi("select nm_dokter from reg_periksa, dokter WHERE reg_periksa.kd_dokter = dokter.kd_dokter AND reg_periksa.no_rawat='"+tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString()+"'"),tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),11).toString(),Sequel.cariIsi("select konsul from rujukan_internal_poli_detail where no_rawat= '"+tbPetugas2.getValueAt(tbPetugas2.getSelectedRow(),1).toString()+"'"));
        LabelNama.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),8).toString());
        LabelNoRM.setText(TNoRM.getText());
        LabelNoRW.setText(TNoRw.getText());
        LabelDokter.setText(TDokter.getText());
       // LabelPoliPerujuk.setText();
        LabelDokterTujuan.setText(Sequel.cariIsi("select dokter.nm_dokter from rujukan_internal_poli, dokter where rujukan_internal_poli.kd_dokter = dokter.kd_dokter AND no_rawat = '"+TNoRw.getText()+"'"));
        LabelPoliTujuan.setText(Sequel.cariIsi("select poliklinik.nm_poli from rujukan_internal_poli, poliklinik where rujukan_internal_poli.kd_poli = poliklinik.kd_poli AND no_rawat = '"+TNoRw.getText()+"'"));
        TKonsulJawab.setText(Sequel.cariIsi("select konsul from rujukan_internal_poli_detail where no_rawat= '"+TNoRw.getText()+"'"));
        TPemeriksaanJawab.setText(Sequel.cariIsi("select pemeriksaan from rujukan_internal_poli_detail where no_rawat= '"+TNoRw.getText()+"'"));
        LabelDiagnosa.setText(Sequel.cariIsi("select diagnosa from rujukan_internal_poli_detail where no_rawat= '"+TNoRw.getText()+"'"));
        TSaranJawab.setText(Sequel.cariIsi("select saran from rujukan_internal_poli_detail where no_rawat= '"+TNoRw.getText()+"'"));
        DlgJawabanKonsul.setVisible(true);
    }//GEN-LAST:event_MnJawabanKonsulActionPerformed

    private void ppDPJPBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ppDPJPBtnPrintActionPerformed
        if(TNoRw.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu pasien...!!!");
        }else{
            DlgDpjp dpjp=new DlgDpjp(null,false);
            dpjp.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dpjp.setLocationRelativeTo(internalFrame1);
            dpjp.isCek();
            dpjp.setdariIGD(TNoRw.getText());
            dpjp.tampil();
            dpjp.setVisible(true);
        }
    }//GEN-LAST:event_ppDPJPBtnPrintActionPerformed

    private void MnDataTriaseIGDBtnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MnDataTriaseIGDBtnPrintActionPerformed
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, data registrasi sudah habis...!!!!");
            TNoRM.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu data pasien dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            if(tbPetugas.getSelectedRow()!= -1){
                this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                RMTriaseIGD form=new RMTriaseIGD(null,false);
                form.isCek();
                form.setNoRm(TNoRw.getText(),TNoRM.getText(),TPasien.getText());
                form.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                form.setLocationRelativeTo(internalFrame1);
                form.setVisible(true);
                this.setCursor(Cursor.getDefaultCursor());
            }
        }
    }//GEN-LAST:event_MnDataTriaseIGDBtnPrintActionPerformed

    /**
    * @data args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(() -> {
            DlgIGD dialog = new DlgIGD(new javax.swing.JFrame(), true);
            dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent e) {
                    System.exit(0);
                }
            });
            dialog.setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private widget.TextBox AsalRujukan;
    private widget.Button BtnAll;
    private widget.Button BtnBatal;
    private widget.Button BtnCari;
    private widget.Button BtnDokter;
    private widget.Button BtnEdit;
    private widget.Button BtnHapus;
    private widget.Button BtnKeluar;
    private widget.Button BtnKeluar2;
    private widget.Button BtnKeluar3;
    private widget.Button BtnKeluar4;
    private widget.Button BtnKeluar6;
    private widget.Button BtnPasien;
    private widget.Button BtnPrint;
    private widget.Button BtnPrint2;
    private widget.Button BtnPrint3;
    private widget.Button BtnPrint4;
    private widget.Button BtnPrint5;
    private widget.Button BtnSeek5;
    private widget.Button BtnSimpan;
    private widget.CekBox ChkInput;
    private widget.CekBox ChkJln;
    private widget.CekBox ChkTracker;
    private widget.ComboBox CmbDetik;
    private widget.ComboBox CmbJam;
    private widget.ComboBox CmbMenit;
    private widget.TextBox CrDokter3;
    private widget.Tanggal DTPCari1;
    private widget.Tanggal DTPCari2;
    private widget.Tanggal DTPReg;
    private javax.swing.JDialog DlgCatatan;
    private javax.swing.JDialog DlgDemografi;
    private javax.swing.JDialog DlgJawabanKonsul;
    private javax.swing.JDialog DlgSakit;
    private javax.swing.JDialog DlgSakit2;
    private widget.PanelBiasa FormInput;
    private widget.TextBox Kabupaten2;
    private widget.TextBox Kd2;
    private widget.TextBox Kecamatan2;
    private widget.TextBox Kelurahan2;
    private widget.Label LCount;
    private widget.Label LabelCatatan;
    private widget.Label LabelDiagnosa;
    private widget.Label LabelDokter;
    private widget.Label LabelDokterTujuan;
    public widget.Label LabelNama;
    private widget.Label LabelNoRM;
    private widget.Label LabelNoRW;
    private widget.Label LabelPoliPerujuk;
    private widget.Label LabelPoliTujuan;
    private javax.swing.JMenu MenuInputData;
    private javax.swing.JMenuItem MnBarcode1;
    private javax.swing.JMenuItem MnBarcodeRM9;
    private javax.swing.JMenuItem MnBatal;
    private javax.swing.JMenuItem MnBelum;
    private javax.swing.JMenuItem MnBilling;
    private javax.swing.JMenuItem MnBillingParsial;
    private javax.swing.JMenuItem MnBlangkoResep;
    private javax.swing.JMenu MnBridging;
    private javax.swing.JMenuItem MnCetakRegister;
    private javax.swing.JMenuItem MnCetakSuratSakit;
    private javax.swing.JMenuItem MnDIrawat;
    private javax.swing.JMenuItem MnDataTriaseIGD;
    private javax.swing.JMenuItem MnDiagnosa;
    private javax.swing.JMenuItem MnDirujuk;
    private javax.swing.JMenu MnGelang;
    private javax.swing.JMenuItem MnGelang1;
    private javax.swing.JMenuItem MnJKRA;
    private javax.swing.JMenuItem MnJadwalOperasi;
    private javax.swing.JMenuItem MnJawabanKonsul;
    private javax.swing.JMenuItem MnLabelTracker;
    private javax.swing.JMenuItem MnLembarCasemix;
    private javax.swing.JMenuItem MnLembarRalan;
    private javax.swing.JMenuItem MnMeninggal;
    private javax.swing.JMenuItem MnNoResep;
    private javax.swing.JMenu MnObat;
    private javax.swing.JMenuItem MnOperasi;
    private javax.swing.JMenuItem MnPemberianObat;
    private javax.swing.JMenuItem MnPeriksaLab;
    private javax.swing.JMenuItem MnPeriksaRadiologi;
    private javax.swing.JMenu MnPermintaan;
    private javax.swing.JMenuItem MnPermintaanLab;
    private javax.swing.JMenuItem MnPermintaanRadiologi;
    private javax.swing.JMenuItem MnPersetujuanMedis;
    private javax.swing.JMenu MnPilihBilling;
    private javax.swing.JMenuItem MnPoliInternal;
    private javax.swing.JMenuItem MnPulangPaksa;
    private javax.swing.JMenuItem MnRawatJalan;
    private javax.swing.JMenuItem MnResepDOkter;
    private javax.swing.JMenuItem MnRujuk;
    private javax.swing.JMenuItem MnRujukMasuk;
    private javax.swing.JMenuItem MnRujukSisrute;
    private javax.swing.JMenu MnRujukan;
    private javax.swing.JMenuItem MnSEP;
    private javax.swing.JMenuItem MnSKDPBPJS;
    private javax.swing.JMenuItem MnSPBK;
    private javax.swing.JMenu MnStatus;
    private javax.swing.JMenuItem MnStatusBaru;
    private javax.swing.JMenuItem MnStatusLama;
    private javax.swing.JMenuItem MnSudah;
    private javax.swing.JMenu MnSurat;
    private javax.swing.JMenuItem MnSuratKontrolVclaim;
    private javax.swing.JMenu MnTindakan;
    private widget.TextBox NoBalasan;
    private widget.TextBox NomorSurat;
    private javax.swing.JPanel PanelInput;
    private widget.ScrollPane Scroll;
    private widget.TextBox TAlmt;
    private widget.TextBox TCari;
    private widget.TextBox TDokter;
    private widget.TextBox THbngn;
    private widget.TextArea TKonsulJawab;
    private widget.TextBox TNoRM;
    private widget.TextBox TNoReg;
    private widget.TextBox TNoRw;
    private widget.TextBox TPasien;
    private widget.TextArea TPemeriksaanJawab;
    private widget.TextBox TPngJwb;
    private widget.TextArea TSaranJawab;
    private widget.TextBox TStatus;
    private widget.Tanggal TglSakit1;
    private widget.Tanggal TglSakit2;
    private widget.Button btnKab;
    private widget.Button btnKec;
    private widget.Button btnKel;
    private widget.Button btnPenjab;
    private widget.Button btnPenjab1;
    private javax.swing.ButtonGroup buttonGroup1;
    private widget.InternalFrame internalFrame1;
    private widget.InternalFrame internalFrame3;
    private widget.InternalFrame internalFrame4;
    private widget.InternalFrame internalFrame5;
    private widget.InternalFrame internalFrame6;
    private widget.InternalFrame internalFrame8;
    private widget.Label jLabel10;
    private widget.Label jLabel13;
    private widget.Label jLabel15;
    private widget.Label jLabel17;
    private widget.Label jLabel18;
    private widget.Label jLabel20;
    private widget.Label jLabel21;
    private widget.Label jLabel22;
    private widget.Label jLabel23;
    private widget.Label jLabel24;
    private widget.Label jLabel26;
    private widget.Label jLabel27;
    private widget.Label jLabel28;
    private widget.Label jLabel29;
    private widget.Label jLabel3;
    private widget.Label jLabel30;
    private widget.Label jLabel31;
    private widget.Label jLabel32;
    private widget.Label jLabel33;
    private widget.Label jLabel34;
    private widget.Label jLabel35;
    private widget.Label jLabel36;
    private widget.Label jLabel37;
    private widget.Label jLabel38;
    private widget.Label jLabel39;
    private widget.Label jLabel4;
    private widget.Label jLabel40;
    private widget.Label jLabel41;
    private widget.Label jLabel42;
    private widget.Label jLabel43;
    private widget.Label jLabel44;
    private widget.Label jLabel45;
    private widget.Label jLabel46;
    private widget.Label jLabel6;
    private widget.Label jLabel7;
    private widget.Label jLabel8;
    private widget.Label jLabel9;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu7;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPopupMenu jPopupMenu1;
    private widget.TextBox kddokter;
    private widget.TextBox kdpnj;
    private widget.TextBox lmsakit;
    private widget.TextBox nmpnj;
    private widget.PanelBiasa panelBiasa2;
    private widget.PanelBiasa panelBiasa3;
    private widget.PanelBiasa panelBiasa4;
    private widget.panelisi panelGlass6;
    private widget.panelisi panelGlass7;
    private javax.swing.JMenuItem ppBerkas;
    private javax.swing.JMenuItem ppBerkasDigital;
    private javax.swing.JMenuItem ppCatatanPasien;
    private javax.swing.JMenuItem ppDPJP;
    private javax.swing.JMenuItem ppGrafikDemografi;
    private javax.swing.JMenuItem ppGrafikPerAgama;
    private javax.swing.JMenuItem ppGrafikPerBulan;
    private javax.swing.JMenuItem ppGrafikPerJK;
    private javax.swing.JMenuItem ppGrafikPerPekerjaan;
    private javax.swing.JMenuItem ppGrafikPerTahun;
    private javax.swing.JMenuItem ppGrafikPerTanggal;
    private javax.swing.JMenuItem ppGrafikPerdokter;
    private javax.swing.JMenuItem ppGrafikPerpoli;
    private javax.swing.JMenuItem ppIKP;
    private javax.swing.JMenuItem ppRiwayat;
    private widget.Table tbPetugas;
    // End of variables declaration//GEN-END:variables

    private void tampil() {
        Valid.tabelKosong(tabMode);   
        try{  
            ps=koneksi.prepareStatement("select reg_periksa.no_reg,reg_periksa.no_rawat,reg_periksa.tgl_registrasi,reg_periksa.jam_reg,"+
                   "reg_periksa.kd_dokter,dokter.nm_dokter,reg_periksa.no_rkm_medis,pasien.nm_pasien,pasien.jk,concat(reg_periksa.umurdaftar,' ',reg_periksa.sttsumur)as umur,poliklinik.nm_poli,"+
                   "reg_periksa.p_jawab,reg_periksa.almt_pj,reg_periksa.hubunganpj,reg_periksa.biaya_reg,reg_periksa.stts_daftar,penjab.png_jawab,reg_periksa.stts,reg_periksa.kd_pj,pasien.tgl_lahir "+
                   "from reg_periksa inner join dokter inner join pasien inner join poliklinik inner join penjab "+
                   "on reg_periksa.kd_dokter=dokter.kd_dokter and reg_periksa.no_rkm_medis=pasien.no_rkm_medis "+
                   "and reg_periksa.kd_pj=penjab.kd_pj and reg_periksa.kd_poli=poliklinik.kd_poli  where  "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.no_reg like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.no_rawat like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.tgl_registrasi like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.kd_dokter like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  dokter.nm_dokter like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.no_rkm_medis like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.stts_daftar like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  pasien.nm_pasien like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  poliklinik.nm_poli like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.p_jawab like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.almt_pj like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  reg_periksa.hubunganpj like ? or "+
                "  poliklinik.kd_poli='IGDK' and tgl_registrasi between ? and ? and  penjab.png_jawab like ? order by reg_periksa.no_rawat "); 
            try {
                ps.setString(1,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(2,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(3,"%"+TCari.getText().trim()+"%");
                ps.setString(4,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(5,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(6,"%"+TCari.getText().trim()+"%");
                ps.setString(7,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(8,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(9,"%"+TCari.getText().trim()+"%");
                ps.setString(10,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(11,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(12,"%"+TCari.getText().trim()+"%");
                ps.setString(13,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(14,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(15,"%"+TCari.getText().trim()+"%");
                ps.setString(16,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(17,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(18,"%"+TCari.getText().trim()+"%");
                ps.setString(19,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(20,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(21,"%"+TCari.getText().trim()+"%");
                ps.setString(22,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(23,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(24,"%"+TCari.getText().trim()+"%");
                ps.setString(25,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(26,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(27,"%"+TCari.getText().trim()+"%");
                ps.setString(28,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(29,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(30,"%"+TCari.getText().trim()+"%");
                ps.setString(31,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(32,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(33,"%"+TCari.getText().trim()+"%");
                ps.setString(34,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(35,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(36,"%"+TCari.getText().trim()+"%");
                ps.setString(37,Valid.SetTgl(DTPCari1.getSelectedItem()+""));
                ps.setString(38,Valid.SetTgl(DTPCari2.getSelectedItem()+""));
                ps.setString(39,"%"+TCari.getText().trim()+"%");
                rs=ps.executeQuery();
                while(rs.next()){
                    tabMode.addRow(new Object[] {false,rs.getString(1),
                                   rs.getString(2),
                                   rs.getString(3),
                                   rs.getString(4),
                                   rs.getString(5),
                                   rs.getString(6),
                                   rs.getString(7),
                                   rs.getString(8),
                                   rs.getString(9),
                                   rs.getString(10),
                                   rs.getString(11),
                                   rs.getString(12),
                                   rs.getString(13),
                                   rs.getString(14),
                                   Valid.SetAngka(rs.getDouble(15)),
                                   rs.getString(16),
                                   rs.getString(17),
                                   rs.getString(18),rs.getString("kd_pj"),
                                   rs.getString("tgl_lahir")
                    });
                } 
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps!=null){
                    ps.close();
                }
            }                   
        }catch(Exception e){
            System.out.println("Notifikasi : "+e);
        }
        LCount.setText(""+tabMode.getRowCount());
    }

    public void emptTeks() {
        TNoReg.setText("");
        TNoRw.setText("");
        Kd2.setText("");
        AsalRujukan.setText("");        
        alamatperujuk="";
        TNoRM.setText("");
        TPasien.setText("");
        TPngJwb.setText("");
        THbngn.setText("");
        TAlmt.setText("");
        TStatus.setText("");
        isNumber();       
        TNoRM.requestFocus();
        kdpnj.setText("");
        nmpnj.setText("");
    }

    private void getData() {
        if(tbPetugas.getSelectedRow()!= -1){
            TNoReg.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),1).toString());
            TNoRw.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
            Kd2.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),2).toString());
            Valid.SetTgl(DTPReg,tbPetugas.getValueAt(tbPetugas.getSelectedRow(),3).toString());
            CmbJam.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(0,2));
            CmbMenit.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(3,5));
            CmbDetik.setSelectedItem(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),4).toString().substring(6,8));
            kddokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),5).toString());
            TDokter.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),6).toString());
            TNoRM.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),7).toString());            
            isCekPasien();
            TPngJwb.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),12).toString());
            TAlmt.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),13).toString());
            THbngn.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),14).toString());
            TStatus.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),16).toString());            
            nmpnj.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),17).toString());
            kdpnj.setText(tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
        }
    }


    private void jam(){
        ActionListener taskPerformer = new ActionListener(){
            private int nilai_jam;
            private int nilai_menit;
            private int nilai_detik;
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "";
                String nol_menit = "";
                String nol_detik = "";
                
                Date now = Calendar.getInstance().getTime();

                // Mengambil nilaj JAM, MENIT, dan DETIK Sekarang
                if(ChkJln.isSelected()==true){
                    nilai_jam = now.getHours();
                    nilai_menit = now.getMinutes();
                    nilai_detik = now.getSeconds();
                }else if(ChkJln.isSelected()==false){
                    nilai_jam =CmbJam.getSelectedIndex();
                    nilai_menit =CmbMenit.getSelectedIndex();
                    nilai_detik =CmbDetik.getSelectedIndex();
                }

                // Jika nilai JAM lebih kecil dari 10 (hanya 1 digit)
                if (nilai_jam <= 9) {
                    // Tambahkan "0" didepannya
                    nol_jam = "0";
                }
                // Jika nilai MENIT lebih kecil dari 10 (hanya 1 digit)
                if (nilai_menit <= 9) {
                    // Tambahkan "0" didepannya
                    nol_menit = "0";
                }
                // Jika nilai DETIK lebih kecil dari 10 (hanya 1 digit)
                if (nilai_detik <= 9) {
                    // Tambahkan "0" didepannya
                    nol_detik = "0";
                }
                // Membuat String JAM, MENIT, DETIK
                String jam = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                // Menampilkan pada Layar
                //tampil_jam.setText("  " + jam + " : " + menit + " : " + detik + "  ");
                CmbJam.setSelectedItem(jam);
                CmbMenit.setSelectedItem(menit);
                CmbDetik.setSelectedItem(detik);
            }
        };
        // Timer
        new Timer(1000, taskPerformer).start();
    }

    private void isPas(){
        if(validasiregistrasi.equals("Yes")){
            if(Sequel.cariInteger("select count(no_rkm_medis) from reg_periksa where no_rkm_medis=? and status_bayar='Belum Bayar' and stts<>'Batal'",TNoRM.getText())>0){
                JOptionPane.showMessageDialog(rootPane,"Maaf, pasien pada kunjungan sebelumnya memiliki tagihan yang belum di closing.\nSilahkan konfirmasi dengan pihak kasir.. !!");
            }else{
                if(validasicatatan.equals("Yes")){
                    if(Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?",TNoRM.getText())>0){
                        this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                        catatan.setNoRm(TNoRM.getText());
                        catatan.setSize(720,330);
                        catatan.setLocationRelativeTo(internalFrame1);
                        catatan.setVisible(true);
                        this.setCursor(Cursor.getDefaultCursor());
                    }
                } 
                isCekPasien();
            }
        }else{
            if(validasicatatan.equals("Yes")){
                if(Sequel.cariInteger("select count(no_rkm_medis) from catatan_pasien where no_rkm_medis=?",TNoRM.getText())>0){
                    this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                    catatan.setNoRm(TNoRM.getText());
                    catatan.setSize(720,330);
                    catatan.setLocationRelativeTo(internalFrame1);
                    catatan.setVisible(true);
                    this.setCursor(Cursor.getDefaultCursor());
                }
            } 
            isCekPasien();
        }        
    }
    
    private void isCekPasien(){
        try {   
            ps3=koneksi.prepareStatement("select nm_pasien,concat(pasien.alamat,', ',kelurahan.nm_kel,', ',kecamatan.nm_kec,', ',kabupaten.nm_kab) asal,"+
                        "namakeluarga,keluarga,pasien.kd_pj,penjab.png_jawab,if(tgl_daftar=?,'Baru','Lama') as daftar, "+
                        "year(from_days(datediff(now(), pasien.tgl_lahir))) as tahun, "+
                        "month(from_days(datediff(now(),pasien.tgl_lahir))) as bulan, "+
                        "day(from_days(datediff(now(),pasien.tgl_lahir))) as hari from pasien "+
                        "inner join kelurahan inner join kecamatan inner join kabupaten inner join penjab "+
                        "on pasien.kd_kel=kelurahan.kd_kel and pasien.kd_pj=penjab.kd_pj "+
                        "and pasien.kd_kec=kecamatan.kd_kec and pasien.kd_kab=kabupaten.kd_kab "+
                        "where pasien.no_rkm_medis=?");
            try {
                ps3.setString(1,Valid.SetTgl(DTPReg.getSelectedItem()+""));
                ps3.setString(2,TNoRM.getText());
                rs=ps3.executeQuery();
                while(rs.next()){
                    TAlmt.setText(rs.getString("asal"));
                    TPngJwb.setText(rs.getString("namakeluarga"));
                    THbngn.setText(rs.getString("keluarga"));
                    kdpnj.setText(rs.getString("kd_pj"));
                    nmpnj.setText(rs.getString("png_jawab"));
                    TStatus.setText(rs.getString("daftar"));
                    umur="0";
                    sttsumur="Th";                    
                    if(rs.getInt("tahun")>0){
                        umur=rs.getString("tahun");
                        sttsumur="Th";
                    }else if(rs.getInt("tahun")==0){
                        if(rs.getInt("bulan")>0){
                            umur=rs.getString("bulan");
                            sttsumur="Bl";
                        }else if(rs.getInt("bulan")==0){
                            umur=rs.getString("hari");
                            sttsumur="Hr";
                        }
                    }
                    TPasien.setText(rs.getString("nm_pasien")+" ("+umur+" "+sttsumur+")");
                }
            } catch (Exception e) {
                System.out.println("Notif : "+e);
            } finally{
                if(rs!=null){
                    rs.close();
                }
                if(ps3!=null){
                    ps3.close();
                }
            }                
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }


    public JTextField getTextField(){
        return TNoRw;
    }

    public JButton getButton(){
        return BtnKeluar;
    }
    
    private void isForm(){
        if(ChkInput.isSelected()==true){
            ChkInput.setVisible(false);
            PanelInput.setPreferredSize(new Dimension(WIDTH,188));
            FormInput.setVisible(true);      
            ChkInput.setVisible(true);
        }else if(ChkInput.isSelected()==false){           
            ChkInput.setVisible(false);            
            PanelInput.setPreferredSize(new Dimension(WIDTH,20));
            FormInput.setVisible(false);      
            ChkInput.setVisible(true);
        }
    }
    
    public void isCek(){
        DTPReg.setDate(new Date());
        DTPCari1.setDate(new Date());
        DTPCari2.setDate(new Date());
        if(var.getrekammedis() == true || var.getadmin()== true){
            BtnSimpan.setEnabled(true);
            BtnEdit.setEnabled(true);
            BtnPrint.setEnabled(true);
            MnSEP.setEnabled(true);
            ppBerkasDigital.setEnabled(true);
            MnRujukMasuk.setEnabled(true);
        }
        BtnHapus.setEnabled(var.getadmin());
        //MnKamarInap.setEnabled(var.getmanajemen());
        if(var.getparamedis() == true || var.getadmin()== true){
            MnRawatJalan.setEnabled(true);
            MnPermintaanLab.setEnabled(true);
            MnPermintaanRadiologi.setEnabled(true);
            MnRujuk.setEnabled(true);
            MnDiagnosa.setEnabled(true);
            MnJadwalOperasi.setEnabled(true);     
            MnSKDPBPJS.setEnabled(true);
            MnOperasi.setEnabled(true);
            ppIKP.setEnabled(true);
            ppCatatanPasien.setEnabled(true);
            ppBerkasDigital.setEnabled(true);
        }
        
        if(var.getapoteker() == true || var.getadmin()== true){
            MnPemberianObat.setEnabled(true);
            MnNoResep.setEnabled(true);
        }
        
        if(var.getkasir() == true || var.getadmin()== true){
            MnBilling.setEnabled(true);
            MnBillingParsial.setEnabled(true);
        }
        
        if(var.getlaboratorium() == true || var.getadmin()== true){
            MnPeriksaLab.setEnabled(true);
        }
        
        if(var.getradiologi()== true || var.getadmin()== true){
            MnPeriksaRadiologi.setEnabled(true);
        }
        
        //MnHapusTagihanOperasi.setEnabled(var.getmanajemen());
        //MnHapusObatOperasi.setEnabled(var.getmanajemen());   
    }
    
    private void isNumber(){
        switch (URUTNOREG) {
            case "poli":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_poli='IGDK' and tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            case "dokter":
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            case "dokter + poli":             
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and kd_poli='IGDK' and tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
            default:
                Valid.autoNomer3("select ifnull(MAX(CONVERT(no_reg,signed)),0) from reg_periksa where kd_dokter='"+kddokter.getText()+"' and tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"'","",3,TNoReg);
                break;
        }
        if(Kd2.getText().equals("")){
            Valid.autoNomer3("select ifnull(MAX(CONVERT(RIGHT(no_rawat,6),signed)),0) from reg_periksa where tgl_registrasi='"+Valid.SetTgl(DTPReg.getSelectedItem()+"")+"' ",dateformat.format(DTPReg.getDate())+"/",6,TNoRw);           
        }            
    }
    
    public  void ctk(){
        try {
            String os = System.getProperty("os.name").toLowerCase();
            Runtime rt = Runtime.getRuntime();
            FileWriter writer = null;
            if(os.contains("win")) {
                writer = new FileWriter("//"+IPPRINTERTRACER);
            }else if (os.contains("mac")) {
                writer = new FileWriter("smb://"+IPPRINTERTRACER);
            }else if (os.contains("nix") || os.contains("nux")) {
                writer = new FileWriter("smb://"+IPPRINTERTRACER);
            }
            writer.write(".: TRACER :.");
            cetakStruk("Draft Sans Serif Condensed", writer,
                    MODE_DRAFT,
                    FONT_SANS_SERIF,
                    CONDENSED_ON,
                    SIZE_12_CPI,
                    SPACING_12_72);
            sendCommand(RESET, writer);
            writer.close();
        } catch (Exception ex) {
             System.out.println("Notif Writer 3 : "+ex);
        }
    }
    
    private  void cetakStruk(String title, FileWriter writer, char[]... mode) throws IOException {
        sendCommand(RESET, writer);
        for (int i = 0; i < mode.length; i++) {
            char[] cmd = mode[i];
            sendCommand(cmd, writer);
        }

        cetakStruk2(title,writer);
	sendCommand(VERTICAL_PRINT_POSITION, writer);
    }
    
    public void sendCommand(char[] command, Writer writer) throws IOException {
        writer.write(command);
    }
    
    private void cetakStruk2(  String title, FileWriter writer){
        String strukFile = "tracerRm.txt";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(strukFile));        
            String tgll= Sequel.cariIsi("select tgl_registrasi from reg_periksa where no_rawat='"+TNoRw.getText()+"'");
            String[] tglref= tgll.split("-");
            boltText(writer);
            writer.write(".: TRACER :.");
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("No. RM      : ");
            boltText(writer);
            writer.write(TNoRM.getText());
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("Nama Pasien : ");
            boltText(writer);
            writer.write(TPasien.getText());
            boltTextOff(writer);
            gantiBaris(writer);
            writer.write("Tgl. Daftar : ");
            boltText(writer);
            writer.write(tglref[2]+"-"+tglref[1]+"-"+tglref[0]+"/"+CmbJam.getSelectedItem()+":"+CmbMenit.getSelectedItem()+":"+CmbDetik.getSelectedItem());
            gantiBaris(writer);
            boltTextOff(writer);
            writer.write("Ruangan     : ");
            boltText(writer);
            writer.write(Sequel.cariIsi("select nm_poli from poliklinik where kd_poli='IGDK'"));
            boltTextOff(writer);

            gantiBaris(writer);
            gantiBaris(writer);
            gantiBaris(writer);
            reader.close();
        } catch (Exception ex) {
            System.out.println("Notif : "+ex);
        }
    }
    
    private void boltText(Writer writer){
        try {
            writer.write(ESC);
            writer.write((char)14);
            writer.write(ESC);
            writer.write('E');
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void boltTextOff(Writer writer) {
        try {
            writer.write(ESC);
            writer.write('F');
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }
    }
    
    private void gantiBaris(Writer writer) {
        try {
            writer.write("\n");
        } catch (Exception e) {
            System.out.println("Notif : "+e);
        }            
    }
    
    private void UpdateUmur(){
        Sequel.mengedit("pasien","no_rkm_medis=?","umur=CONCAT(CONCAT(CONCAT(TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()), ' Th '),CONCAT(TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12), ' Bl ')),CONCAT(TIMESTAMPDIFF(DAY, DATE_ADD(DATE_ADD(tgl_lahir,INTERVAL TIMESTAMPDIFF(YEAR, tgl_lahir, CURDATE()) YEAR), INTERVAL TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) - ((TIMESTAMPDIFF(MONTH, tgl_lahir, CURDATE()) div 12) * 12) MONTH), CURDATE()), ' Hr'))",1,new String[]{TNoRM.getText()});
    }
    
    private void billingprasial() {
        if(tbPetugas.getSelectedRow()!= -1){
            jmlparsial=0;
            if(aktifkanparsial.equals("yes")){
                jmlparsial=Sequel.cariInteger("select count(kd_pj) from set_input_parsial where kd_pj=?",tbPetugas.getValueAt(tbPetugas.getSelectedRow(),19).toString());
            }
            if(jmlparsial>0){
                DlgBilingParsialRalan parsialralan=new DlgBilingParsialRalan(null,false);
                parsialralan.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
                parsialralan.setLocationRelativeTo(internalFrame1);
                //parsialralan.emptTeks();
                parsialralan.isCek();
                parsialralan.setNoRm(TNoRw.getText(),kddokter.getText(),TDokter.getText(),"IGDK");
                parsialralan.setVisible(true);
            }else{
                JOptionPane.showMessageDialog(null,"Maaf, Cara bayar "+tbPetugas.getValueAt(tbPetugas.getSelectedRow(),17).toString()+" tidak diijinkan menggunakan Billing Parsial...!!!");
            }
        }else{
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        } 
    }
    
    public void SetPasien(String norm,String nosisrute,String FaskesAsal){
        ChkInput.setSelected(true);
        isForm(); 
        TNoRM.setText(norm);
        this.nosisrute=nosisrute;
        AsalRujukan.setText(FaskesAsal);
        isPas();
    }
    
    public void setPasien(String NamaPasien,String Kontak,String Alamat,String TempatLahir,String TglLahir,
            String JK,String NoKartuJKN,String NIK,String nosisrute,String FaskesAsal){
        var.setform("DlgIGD");
        ChkInput.setSelected(true);
        isForm(); 
        pasien.emptTeks();
        pasien.isCek();
        this.nosisrute=nosisrute;
        AsalRujukan.setText(FaskesAsal);
        pasien.setPasien(NamaPasien, Kontak, Alamat, TempatLahir, TglLahir, JK, NoKartuJKN, NIK);
        pasien.setSize(internalFrame1.getWidth()-20,internalFrame1.getHeight()-20);
        pasien.setLocationRelativeTo(internalFrame1);
        pasien.setVisible(true);
    }
    
    public void kamar_inap(){
        if(tabMode.getRowCount()==0){
            JOptionPane.showMessageDialog(null,"Maaf, table masih kosong...!!!!");
            TNoReg.requestFocus();
        }else if(TPasien.getText().trim().equals("")){
            JOptionPane.showMessageDialog(null,"Maaf, Silahkan anda pilih dulu dengan menklik data pada table...!!!");
            tbPetugas.requestFocus();
        }else{
            var.setstatus(true);
            DlgKamarInap dlgki=new DlgKamarInap(null,false);
            dlgki.setSize(internalFrame1.getWidth(),internalFrame1.getHeight());
            dlgki.setLocationRelativeTo(internalFrame1);
            dlgki.emptTeks();
            dlgki.isCek();
            dlgki.setNoRm(TNoRw.getText());
            dlgki.setVisible(true);
        }
    }
}
