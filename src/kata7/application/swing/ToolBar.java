package kata7.application.swing;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import kata7.control.Command;
import kata7.model.Attribute;
import kata7.view.AttributeDialog;
import kata7.view.PopulationDialog;

public class ToolBar extends JPanel implements AttributeDialog, PopulationDialog{

    private final Map <String, Command> commands;
    private final List <Attribute> attributes = new ArrayList<>();
    private JComboBox combo;

    public ToolBar(Map<String, Command> commands) {
        super();
        this.commands = commands;
        this.add(mailDomainAttribute());
        this.add(firstCharAttribute());
        this.add(comboBox());
        this.add(calculateButton());
    }
    
    @Override
    public Attribute<Person,String> attribute() {
        return attributes.get(combo.getSelectedIndex());
    }

    @Override
    public List population() {
        try {
            return MailReader.read("C:\\Users\\WonkaD\\Documents\\NetBeansProjects\\Kata7\\src\\kata7\\emails.txt");
        } catch (IOException ex) {
            return new ArrayList();
        }
    }

    private void add (Attribute attribute){
        this.attributes.add(attribute);
    }
    
    private Attribute mailDomainAttribute() {
        return (Attribute <Person,String>) (Person item) -> item.getMail().split("@")[1];
    }

    private Attribute firstCharAttribute() {
        return (Attribute <Person,Character>) (Person item) ->item.getMail().charAt(0);
    }

    private JComboBox comboBox() {
        this.combo = new JComboBox(options("Mail Domain","First Char"));
        return combo;
    }
    
    private String[] options(String... options) {
        return options;
    }

    private JButton calculateButton() {
        JButton button =  new JButton("Calculate");
        button.addActionListener((ActionEvent ae) -> {commands.get("calculate").execute();});
        return  button;
    }

    
}
