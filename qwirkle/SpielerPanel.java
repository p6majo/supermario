package qwirkle;

import javax.swing.*;
import java.awt.*;

/**
 * The class SpielerPanel
 *
 * @author p6majo
 * @version 2019-06-20
 */
public class SpielerPanel extends JPanel {
    private JRadioButton aktivRadioButton;
    private JTextField nameTextField;
    private JTextField punkteTextFeld;
    private JPanel spielerPanel;

    /*
     *********************************************
     ***           Attributes           **********
     *********************************************
     */



    /*
     **********************************************
     ****           Constructors         **********
     **********************************************
     */

    public SpielerPanel(){
        erzeugeUI();
    }

    /*
     ***********************************************
     ***           Getters              ************
     ***********************************************
     */



    /*
     ***********************************************
     ***           Setters              ************
     ***********************************************
     */

    public void setzeSpielerName(String pName) {
        nameTextField.setText(pName);
    }

    public void setzeAktiv(boolean pAktiv) {
        aktivRadioButton.setSelected(pAktiv);
    }

    public void setzePunkte(int pPunkte) {
        //erzeuge Nullenpadding
        String punkteString = pPunkte + "";
        while (punkteString.length() < 4)
            punkteString = "0" + punkteString;
        punkteTextFeld.setText(punkteString);
    }
    /*
     ***********************************************
     ***           Public methods       ************
     ***********************************************
     */


    public void setzeLeer(boolean leer){
        this.aktivRadioButton.setVisible(!leer);
        this.punkteTextFeld.setVisible(!leer);
        this.nameTextField.setVisible(!leer);
    }
    /*
     ***********************************************
     ***           Private methods      ************
     ***********************************************
     */


    /*
     ***********************************************
     ***           Overrides            ************
     ***********************************************
     */

    /*
     ***********************************************
     ***           toString             ************
     ***********************************************
     */

    @Override
    public String toString() {
        return super.toString();
    }



    private void erzeugeUI() {
        spielerPanel = new JPanel();
        spielerPanel.setLayout(new GridBagLayout());
        spielerPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createRaisedBevelBorder(), null));
        aktivRadioButton = new JRadioButton();
        aktivRadioButton.setText("");
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.weighty = 1.0;
        spielerPanel.add(aktivRadioButton, gbc);
        nameTextField = new JTextField();
        Font nameTextFieldFont = this.erzeugeSchrift("Open Sans Extrabold", Font.BOLD, 20, nameTextField.getFont());
        if (nameTextFieldFont != null) nameTextField.setFont(nameTextFieldFont);
        nameTextField.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.8;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;
        gbc.ipady = 10;
        gbc.insets = new Insets(5, 5, 5, 5);
        spielerPanel.add(nameTextField, gbc);
        punkteTextFeld = new JTextField();
        punkteTextFeld.setEnabled(false);
        Font punkteTextFeldFont = this.erzeugeSchrift("Open Sans Extrabold", Font.BOLD, 20, punkteTextFeld.getFont());
        if (punkteTextFeldFont != null) punkteTextFeld.setFont(punkteTextFeldFont);
        punkteTextFeld.setHorizontalAlignment(0);
        gbc = new GridBagConstraints();
        gbc.gridx = 5;
        gbc.gridy = 1;
        gbc.weightx = 0.2;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.ipadx = 20;
        gbc.ipady = 19;
        spielerPanel.add(punkteTextFeld, gbc);
        final JPanel spacer1 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 4;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        spielerPanel.add(spacer1, gbc);
        final JPanel spacer2 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        spielerPanel.add(spacer2, gbc);
        final JPanel spacer3 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        spielerPanel.add(spacer3, gbc);
        final JPanel spacer4 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        spielerPanel.add(spacer4, gbc);
        final JPanel spacer5 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        spielerPanel.add(spacer5, gbc);
        final JPanel spacer6 = new JPanel();
        gbc = new GridBagConstraints();
        gbc.gridx = 6;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.VERTICAL;
        spielerPanel.add(spacer6, gbc);
    }

    /**
     * @noinspection ALL
     */
    private Font erzeugeSchrift(String fontName, int style, int size, Font currentFont) {
        if (currentFont == null) return null;
        String resultName;
        if (fontName == null) {
            resultName = currentFont.getName();
        } else {
            Font testFont = new Font(fontName, Font.PLAIN, 10);
            if (testFont.canDisplay('a') && testFont.canDisplay('1')) {
                resultName = fontName;
            } else {
                resultName = currentFont.getName();
            }
        }
        return new Font(resultName, style >= 0 ? style : currentFont.getStyle(), size >= 0 ? size : currentFont.getSize());
    }


    public JComponent gibHauptKomponente() {
        return spielerPanel;
    }
}
