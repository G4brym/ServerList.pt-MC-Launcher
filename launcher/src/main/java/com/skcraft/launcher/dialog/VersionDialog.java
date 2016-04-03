package com.skcraft.launcher.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.WindowConstants;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import com.skcraft.launcher.Launcher;
import com.skcraft.launcher.swing.FormPanel;
import com.skcraft.launcher.swing.LinedBoxPanel;
import com.skcraft.launcher.swing.SwingHelper;
import com.skcraft.launcher.util.SharedLocale;

public class VersionDialog extends JDialog {

    private final Launcher launcher;
    static @Getter @Setter public String version;
    static @Getter @Setter public Window wind;
    
    static @Getter @Setter public String lastVersion;
    static @Getter @Setter public List versions;
    
    private final JComboBox versionCombo = new JComboBox();
    private final JButton okButton = new JButton(SharedLocale.tr("button.ok"));
    private final JButton cancelButton = new JButton(SharedLocale.tr("button.cancel"));
    
    private final FormPanel formPanel = new FormPanel();
    private final LinedBoxPanel buttonsPanel = new LinedBoxPanel(true);

    /**
     * Create a new login dialog.
     *
     * @param owner the owner
     * @param launcher the launcher
     */
    public VersionDialog(Window owner, @NonNull Launcher launcher) {
        super(owner, ModalityType.DOCUMENT_MODAL);

        this.launcher = launcher;

        setTitle(SharedLocale.tr("version.title"));
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(420, 0));
        setResizable(false);
        pack();
        setLocationRelativeTo(owner);

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                dispose();
            }
        });
    }

    private void initComponents() {
    	
        versionCombo.setEditable(false);
        
    	for(int i=0; i<versions.size(); i++){
    		String versionID = versions.get(i).toString().replace("{version=", "").replace("}", "");
    		versionCombo.addItem(versionID);
    	}
    	
        versionCombo.getEditor().selectAll();
        
        formPanel.addRow(new JLabel(SharedLocale.tr("login.version")), versionCombo);

        okButton.setFont(okButton.getFont().deriveFont(Font.BOLD)); 

        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(26, 13, 13, 13));

        buttonsPanel.addGlue();
        buttonsPanel.addElement(okButton);
        buttonsPanel.addElement(cancelButton);

        add(formPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        getRootPane().setDefaultButton(okButton);

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
				 setVersion(versionCombo.getSelectedItem().toString());
				 setVisible(false);
                dispose();
            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            	setVisible(false);
                dispose();
            }
        });

    }
    public static String showVersionRequest(Window owner, Launcher launcher) {
    	VersionDialog dialog = new VersionDialog(owner, launcher);
    	dialog.setVisible(true);
    	return dialog.getVersion();
    }

}