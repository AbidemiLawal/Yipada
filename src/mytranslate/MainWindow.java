package mytranslate;

import java.awt.Dialog.ModalExclusionType;
import yipada.*;
import java.io.*;

public class MainWindow extends javax.swing.JFrame {

    InputOutput o_IO;
    compiler oCompiler = null;
    Linker oLinker = null;
    Processor oProcessor = null;
    Assembler oAssembler;
    Interpreter oInterPreter;
    Process P = null;
    String sAppDirectory = null;

    /** Creates new form MainWindow */
    void printCurrentDirectory() {
        String cwd = null;

       File dir1 = new File(".");
        File dir2 = new File("..");
        try {
            System.out.println("Current dir : " + dir1.getCanonicalPath());
            System.out.println("Parent  dir : " + dir2.getCanonicalPath());
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    public MainWindow() {
        initComponents();
        o_IO = new InputOutput(jTextOutPutWindow);
        o_IO.output("");
        sAppDirectory = System.getProperty("user.dir");

        this.jMnuProgramCompile.setVisible(true);
        jMnuProgramRUn.setEnabled(false);

        printCurrentDirectory();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDlgHelpAbout = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jDlgHelpAboutClose = new javax.swing.JButton();
        jDlgNewProject = new javax.swing.JDialog();
        jtxtNewProDlg_prompt = new javax.swing.JLabel();
        jtxtNewProjectDlgProName = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jtxtNewProjecDlgProtLocation = new javax.swing.JTextField();
        btnNewProjectChooseLocation = new javax.swing.JButton();
        jbtnOK = new javax.swing.JButton();
        jbtnCancel = new javax.swing.JButton();
        jlblPrompt = new javax.swing.JLabel();
        jFileChooser1 = new javax.swing.JFileChooser();
        jToolBar1 = new javax.swing.JToolBar();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTxtEditor = new javax.swing.JTextArea();
        jlblStatusBar = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextOutPutWindow = new javax.swing.JTextArea();
        jText_INPUT = new javax.swing.JTextField();
        jMnuMain = new javax.swing.JMenuBar();
        JMnuFile = new javax.swing.JMenu();
        jmnuNewProject = new javax.swing.JMenuItem();
        jmnuNewFile = new javax.swing.JMenuItem();
        jmnuOpenProject = new javax.swing.JMenuItem();
        jmnuOpenFile = new javax.swing.JMenuItem();
        jMnuFileSave = new javax.swing.JMenuItem();
        jmnuSaveAs = new javax.swing.JMenuItem();
        jmnuSaveAll = new javax.swing.JMenuItem();
        jMnuFilePrint = new javax.swing.JMenuItem();
        jMnuFileExit = new javax.swing.JMenuItem();
        jMnuEdit = new javax.swing.JMenu();
        jMnuProgram = new javax.swing.JMenu();
        jMnuProgramCompile = new javax.swing.JMenuItem();
        jMnuProgramRUn = new javax.swing.JMenuItem();
        jHelp = new javax.swing.JMenu();
        jMnuHelpContents = new javax.swing.JMenuItem();
        jMnuHelpIndex = new javax.swing.JMenuItem();
        jMnuHelpOnline = new javax.swing.JMenuItem();
        jMnuHelpAbout = new javax.swing.JMenuItem();

        jDlgHelpAbout.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDlgHelpAbout.setAlwaysOnTop(true);
        jDlgHelpAbout.setModal(true);

        jLabel1.setText("Author : Adekoya Adekunle R");

        jLabel2.setText("Version : 2.0");

        jDlgHelpAboutClose.setText("Close");
        jDlgHelpAboutClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jDlgHelpAboutCloseActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jDlgHelpAboutLayout = new javax.swing.GroupLayout(jDlgHelpAbout.getContentPane());
        jDlgHelpAbout.getContentPane().setLayout(jDlgHelpAboutLayout);
        jDlgHelpAboutLayout.setHorizontalGroup(
            jDlgHelpAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDlgHelpAboutLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jDlgHelpAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jDlgHelpAboutLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(63, 63, 63))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(41, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDlgHelpAboutLayout.createSequentialGroup()
                .addContainerGap(134, Short.MAX_VALUE)
                .addComponent(jDlgHelpAboutClose))
        );
        jDlgHelpAboutLayout.setVerticalGroup(
            jDlgHelpAboutLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDlgHelpAboutLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jDlgHelpAboutClose))
        );

        jDlgNewProject.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jDlgNewProject.setBounds(new java.awt.Rectangle(0, 0, 559, 195));
        jDlgNewProject.setModal(true);

        jtxtNewProDlg_prompt.setText("Project Name");

        jLabel4.setText("Project Location");

        btnNewProjectChooseLocation.setText("Browse");
        btnNewProjectChooseLocation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNewProjectChooseLocationActionPerformed(evt);
            }
        });

        jbtnOK.setText("OK");
        jbtnOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jbtnOKActionPerformed(evt);
            }
        });

        jbtnCancel.setText("Cancel");

        jlblPrompt.setForeground(new java.awt.Color(255, 0, 0));

        javax.swing.GroupLayout jDlgNewProjectLayout = new javax.swing.GroupLayout(jDlgNewProject.getContentPane());
        jDlgNewProject.getContentPane().setLayout(jDlgNewProjectLayout);
        jDlgNewProjectLayout.setHorizontalGroup(
            jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jDlgNewProjectLayout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jtxtNewProDlg_prompt)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 51, Short.MAX_VALUE)
                .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jDlgNewProjectLayout.createSequentialGroup()
                        .addComponent(jbtnOK, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jbtnCancel, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jDlgNewProjectLayout.createSequentialGroup()
                        .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jtxtNewProjectDlgProName, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jtxtNewProjecDlgProtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnNewProjectChooseLocation)))
                .addGap(10, 10, 10))
            .addGroup(jDlgNewProjectLayout.createSequentialGroup()
                .addGap(166, 166, 166)
                .addComponent(jlblPrompt, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(166, Short.MAX_VALUE))
        );
        jDlgNewProjectLayout.setVerticalGroup(
            jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDlgNewProjectLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(jlblPrompt)
                .addGap(18, 18, 18)
                .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtxtNewProDlg_prompt)
                    .addComponent(jtxtNewProjectDlgProName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jtxtNewProjecDlgProtLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNewProjectChooseLocation)))
                .addGap(18, 18, 18)
                .addGroup(jDlgNewProjectLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jbtnOK)
                    .addComponent(jbtnCancel))
                .addGap(45, 45, 45))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jToolBar1.setRollover(true);
        jToolBar1.setAlignmentX(0.0F);

        jTxtEditor.setColumns(20);
        jTxtEditor.setRows(5);
        jTxtEditor.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTxtEditorKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTxtEditor);

        jlblStatusBar.setText("(c) YIPADA");

        jTextOutPutWindow.setColumns(20);
        jTextOutPutWindow.setRows(5);
        jScrollPane2.setViewportView(jTextOutPutWindow);

        jText_INPUT.setText("?");
        jText_INPUT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jText_INPUTActionPerformed(evt);
            }
        });
        jText_INPUT.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jText_INPUTKeyPressed(evt);
            }
        });

        JMnuFile.setText("File");

        jmnuNewProject.setText("New Project");
        jmnuNewProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuNewProjectActionPerformed(evt);
            }
        });
        JMnuFile.add(jmnuNewProject);

        jmnuNewFile.setText("New File");
        JMnuFile.add(jmnuNewFile);

        jmnuOpenProject.setText("Open Project...");
        jmnuOpenProject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jmnuOpenProjectActionPerformed(evt);
            }
        });
        JMnuFile.add(jmnuOpenProject);

        jmnuOpenFile.setText("Open File");
        JMnuFile.add(jmnuOpenFile);

        jMnuFileSave.setText("Save");
        JMnuFile.add(jMnuFileSave);

        jmnuSaveAs.setText("Save As");
        JMnuFile.add(jmnuSaveAs);

        jmnuSaveAll.setText("Save All");
        JMnuFile.add(jmnuSaveAll);

        jMnuFilePrint.setText("Print...");
        JMnuFile.add(jMnuFilePrint);

        jMnuFileExit.setText("Exit");
        jMnuFileExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMnuFileExitMouseClicked(evt);
            }
        });
        jMnuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuFileExitActionPerformed(evt);
            }
        });
        JMnuFile.add(jMnuFileExit);

        jMnuMain.add(JMnuFile);
        jMnuMain.add(jMnuEdit);

        jMnuProgram.setText("Program");
        jMnuProgram.setPreferredSize(new java.awt.Dimension(87, 22));

        jMnuProgramCompile.setText("Compile  ");
        jMnuProgramCompile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuProgramCompileActionPerformed(evt);
            }
        });
        jMnuProgram.add(jMnuProgramCompile);

        jMnuProgramRUn.setText("Run");
        jMnuProgramRUn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuProgramRUnActionPerformed(evt);
            }
        });
        jMnuProgram.add(jMnuProgramRUn);

        jMnuMain.add(jMnuProgram);

        jHelp.setText("Help");

        jMnuHelpContents.setText("Contents");
        jHelp.add(jMnuHelpContents);

        jMnuHelpIndex.setText("Index");
        jHelp.add(jMnuHelpIndex);

        jMnuHelpOnline.setText("Online ");
        jHelp.add(jMnuHelpOnline);

        jMnuHelpAbout.setText("About");
        jMnuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMnuHelpAboutActionPerformed(evt);
            }
        });
        jHelp.add(jMnuHelpAbout);

        jMnuMain.add(jHelp);

        setJMenuBar(jMnuMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jToolBar1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addComponent(jlblStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
            .addComponent(jText_INPUT, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jText_INPUT, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jlblStatusBar, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMnuFileExitMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMnuFileExitMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jMnuFileExitMouseClicked

    private void jMnuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuFileExitActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_jMnuFileExitActionPerformed

    private void jDlgHelpAboutCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jDlgHelpAboutCloseActionPerformed
        // TODO add your handling code here:
        printCurrentDirectory();
        jDlgHelpAbout.setVisible(false);
    }//GEN-LAST:event_jDlgHelpAboutCloseActionPerformed

    private void jMnuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuHelpAboutActionPerformed
        // TODO add your handling code here:
        jDlgHelpAbout.setSize(300, 150);
        jDlgHelpAbout.setVisible(true);
    }//GEN-LAST:event_jMnuHelpAboutActionPerformed

    private void jMnuProgramCompileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuProgramCompileActionPerformed
        // TODO add your handling code here:
        try {
            o_IO.output("");
            oCompiler = null;
            oCompiler = new compiler(this.o_IO,
                    this.sAppDirectory,
                    this.jTxtEditor);

            if (oCompiler.Compile(jTxtEditor)) {
                // compilation works...proceed to link
                this.o_IO.appendOutPutWindow("Completed!!!\n");
                String sPath2CompiledCode = (oCompiler.getPath2CompiledCode());
                oLinker = null;
                oLinker = new Linker(this.o_IO, this.sAppDirectory);

                if (oLinker.Link(sPath2CompiledCode)) {
                    // link successful...proceed to process d executable
                    //this.o_IO.appendOutPutWindow("Linking OK!!!\n");
                    jMnuProgramRUn.setEnabled(true);
                }
                this.P = null;  // discard handle to processor after linking

            }
        } catch (Exception ex) {
            this.o_IO.appendOutPutWindow("IDE ERROR::" + ex.getMessage() + "\n");
        }

    }//GEN-LAST:event_jMnuProgramCompileActionPerformed

    private void jMnuProgramRUnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMnuProgramRUnActionPerformed
        // TODO add your handling code here:
        try {
            // this.o_IO.output(sAppDirectory)
            oProcessor = new Processor(o_IO);
            oProcessor.Execute(oLinker.getExecutableFile());
            // oProcessor = null;
            oLinker = null;
            oCompiler = null;
            jMnuProgramRUn.setEnabled(false);
        } catch (Exception ex) {
            this.o_IO.appendOutPutWindow("IDE ERROR::" + ex.getMessage() + "\n");
        }
    }//GEN-LAST:event_jMnuProgramRUnActionPerformed

    private void jmnuNewProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuNewProjectActionPerformed

        jDlgNewProject.setVisible(true);
        //  jDlgNewProject.show();

    }//GEN-LAST:event_jmnuNewProjectActionPerformed

    private void btnNewProjectChooseLocationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNewProjectChooseLocationActionPerformed
        // TODO add your handling code here:

        javax.swing.JFileChooser oChooser = new javax.swing.JFileChooser();
        oChooser.setFileSelectionMode(javax.swing.JFileChooser.DIRECTORIES_ONLY);
        oChooser.setApproveButtonText("Select");
        oChooser.showOpenDialog(this);
        this.jtxtNewProjecDlgProtLocation.setText(oChooser.getSelectedFile().getAbsolutePath());
        //  System.out.println("getCurrentDirectory(): "    +  oChooser.getSelectedFile().getAbsolutePath());

    }//GEN-LAST:event_btnNewProjectChooseLocationActionPerformed

    private void openProject() {
        javax.swing.JFileChooser oChooser = new javax.swing.JFileChooser();
        // oChooser.setFileSelectionMode();
        oChooser.setApproveButtonText("Select");
        oChooser.showOpenDialog(this);
        String sProjectName = "";
        sProjectName = oChooser.getSelectedFile().getAbsolutePath();
        if (validProjectName(sProjectName)) {
            // project name is valid...so load into project explorer
            System.out.println(sProjectName + " Open Ok!");
        }
    }
    private void jmnuOpenProjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jmnuOpenProjectActionPerformed

        openProject();
        // TODO add your handling code here:
    }//GEN-LAST:event_jmnuOpenProjectActionPerformed
    private boolean validProjectName(String sProjectName) {
        // non existing project name...must b unique for all project types
        return true;
    }

    private boolean validProjectLocation(String sProjectLocation) {
        // valid project directory expected...
        return true;
    }

    private boolean bValidateNewProjectDetails() {
        try {
            String sProjectName = this.jtxtNewProjectDlgProName.getText();
            String sProjectLocation = this.jtxtNewProjecDlgProtLocation.getText();
            if (!validProjectName(sProjectName)) {
                return false;
            }
            return true;
        } catch (Exception ex) {
            return false;
        }

    }
    private void jbtnOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jbtnOKActionPerformed
        // TODO add your handling code here:
        bValidateNewProjectDetails();
    }//GEN-LAST:event_jbtnOKActionPerformed

    private void jTxtEditorKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTxtEditorKeyPressed
        // TODO add your handling code here:
        //System.out.println("key pressed : " + evt.getKeyCode()) ;
        // key code for enter key is 10
    }//GEN-LAST:event_jTxtEditorKeyPressed

    private void jText_INPUTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jText_INPUTActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jText_INPUTActionPerformed

    private void jText_INPUTKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jText_INPUTKeyPressed
        // TODO add your handling code here:
        String sdata = jText_INPUT.getText();
        if (sdata.trim().equals("")) {
            jText_INPUT.setText("?");
        } else if (sdata.trim().substring(0, 1).equals("?") == false) {
            sdata = "?" + sdata;
            jText_INPUT.setText(sdata);
        }

        if (evt.getKeyCode() == 10) {
            UserInputProcessor oUserInputProcessor = new UserInputProcessor();
            oUserInputProcessor.sendUserInput2StandardInput(
                    this.P, this.jText_INPUT.getText());
            oUserInputProcessor = null;
            jText_INPUT.setText("?");
        } // else [enter] key is not pressed!
    }//GEN-LAST:event_jText_INPUTKeyPressed
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu JMnuFile;
    private javax.swing.JButton btnNewProjectChooseLocation;
    private javax.swing.JDialog jDlgHelpAbout;
    private javax.swing.JButton jDlgHelpAboutClose;
    private javax.swing.JDialog jDlgNewProject;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JMenu jHelp;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenu jMnuEdit;
    private javax.swing.JMenuItem jMnuFileExit;
    private javax.swing.JMenuItem jMnuFilePrint;
    private javax.swing.JMenuItem jMnuFileSave;
    private javax.swing.JMenuItem jMnuHelpAbout;
    private javax.swing.JMenuItem jMnuHelpContents;
    private javax.swing.JMenuItem jMnuHelpIndex;
    private javax.swing.JMenuItem jMnuHelpOnline;
    private javax.swing.JMenuBar jMnuMain;
    private javax.swing.JMenu jMnuProgram;
    private javax.swing.JMenuItem jMnuProgramCompile;
    private javax.swing.JMenuItem jMnuProgramRUn;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextOutPutWindow;
    private javax.swing.JTextField jText_INPUT;
    private javax.swing.JToolBar jToolBar1;
    private javax.swing.JTextArea jTxtEditor;
    private javax.swing.JButton jbtnCancel;
    private javax.swing.JButton jbtnOK;
    private javax.swing.JLabel jlblPrompt;
    private javax.swing.JLabel jlblStatusBar;
    private javax.swing.JMenuItem jmnuNewFile;
    private javax.swing.JMenuItem jmnuNewProject;
    private javax.swing.JMenuItem jmnuOpenFile;
    private javax.swing.JMenuItem jmnuOpenProject;
    private javax.swing.JMenuItem jmnuSaveAll;
    private javax.swing.JMenuItem jmnuSaveAs;
    private javax.swing.JLabel jtxtNewProDlg_prompt;
    private javax.swing.JTextField jtxtNewProjecDlgProtLocation;
    private javax.swing.JTextField jtxtNewProjectDlgProName;
    // End of variables declaration//GEN-END:variables
}
