/* Project 		: Project6KHang
 * Class 		: CoffeeCostShop
 * Date			: 04/28/2017
 * Programmer	: Kathleen Hang
 * Description	: This application is used to create and calculate coffee orders for customers. The user will
 * 				  be able to select coffee types, add, clear, and display results with option for decaffeinated.
 */

import java.awt.Font;
import java.awt.event.*; // for the item and action listeners
import java.text.DecimalFormat; // format the coffee prices with "$0.00"
import javax.swing.*;


public class CoffeeCostShop extends JFrame implements ActionListener, ItemListener
{
	// Objects to enter input and display
	// Coffee types to populate the combo box
	// String array to populate the combo box
	String coffeeString [] = {"[[ Select a Coffee ]]", "Mocha", "Latte", "Drip"};
	JComboBox coffeeComboBox = new JComboBox(coffeeString);
	JCheckBox decafCheckBox = new JCheckBox("Decaffeinated?                  ");
	JLabel unitPriceLabel = new JLabel("Unit Price $0.00        ");
	
	JTextField nameTextField = new JTextField(20);
	JTextField quantityTextField = new JTextField(3);
	JButton addButton = new JButton("		Add to Order		");
	JButton completeButton = new JButton("		Complete Order		");
	JButton clearButton = new JButton("		Clear		");
	JTextArea outputTextArea = new JTextArea(10,45);
	JScrollPane outputScrollPane = new JScrollPane(outputTextArea);
	JLabel companyNameLabel = new JLabel("------ CoffeeDuck - Morning Coffee Stop ------   ");
	Font titleFont = new Font("Arial",Font.PLAIN, 18);
	Font courierFont = new Font("Courier", Font.PLAIN, 13);
	JLabel programmerNameLabel = new JLabel("Programmed by: Kathleen Hang");
	JPanel mainPanel = new JPanel();	

	// instance variables to store the width and height of window
	final int WIDTH_INTEGER = 400, HEIGHT_INTEGER = 420;

	// main method - instantiate the class & allow window to be closed with x button
	public static void main(String[] args)
	{
		CoffeeCostShop myProgram = new CoffeeCostShop();
		myProgram.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/*CoffeeCostShop - Constructor
	 * Calls designClass to add components on the main panel
	 * Calls addListeners method to add listeners to the components
	 * Add the main panel to the frame
	 * Set the size and visibility of the frame
	 * Add title bar to the text area
	 */
	
	public CoffeeCostShop()
	{
		designClass();
		addListeners();
		add(mainPanel);
		setSize(WIDTH_INTEGER, HEIGHT_INTEGER);
		setVisible(true);
		String titleString = String.format("%-8s %-8s %-8s %-7s %-7s %n", "Name:", "Qty", "Type", "Decaf", "Total");
		outputTextArea.append(titleString);
	}

	/*
	 * designClass method
	 * - Disable the complete order button 
	 * Add to main panel:
	 * - Add the company name label, prompts and textfields for name and quantity
	 * - Add the text area to the main panel to display the coffee calculations
	 * - Add the coffee combo box so the user can select desired coffee type
	 * - Add decaf check box for choice of decaffeinated coffee or not
	 * - Add unit price label to show coffee unit cost
	 * - Add the add, complete, and clear button to edit and display coffee order
	 * - Add a scroll pane and programmer name label
	 */
	public void designClass()
	{
		// call methods to add components to main panel
		completeButton.setEnabled(false);
		companyNameLabel.setFont(titleFont);
		outputTextArea.setFont(courierFont);
		mainPanel.add(companyNameLabel);
		mainPanel.add(new JLabel(" Name		"));
		mainPanel.add(nameTextField);
		
		mainPanel.add(coffeeComboBox);
		mainPanel.add(decafCheckBox);
		mainPanel.add(unitPriceLabel);
		mainPanel.add(new JLabel("               Quantity"));
		mainPanel.add(quantityTextField);
		
		mainPanel.add(addButton);
		mainPanel.add(completeButton);
		mainPanel.add(clearButton);
		mainPanel.add(outputScrollPane);
		mainPanel.add(programmerNameLabel);
	}
	
	/* addListeners method
	 * - Add the action listener to the add, complete, and clear buttons
	 * - Add the item listener to the decaf check box
	 * - Add the item listener to the coffee combo box
	 * - Add the action listener to the quantityTextField
	 */
	public void addListeners()
	{
		quantityTextField.addActionListener(this);
		addButton.addActionListener(this);
		completeButton.addActionListener(this);
		clearButton.addActionListener(this);
		decafCheckBox.addItemListener(this);
		coffeeComboBox.addItemListener(this);
	}
	
	/* itemStateChanged method
	 * When item listener to the coffee combo box fires an event
	 * ***coffee combo box index integer is passed into the parameters of the newly created object myCalc 
	 * 	  of CoffeeCostCalculation class. The coffee unit price to be displayed will be retrieved from the other class.
	 */
	public void itemStateChanged(ItemEvent evt)
	{
		// combo box fires this event
		Object sourceObject = evt.getSource();
		int indexInteger;
		
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		if(evt.getSource() == coffeeComboBox)
		{
			indexInteger = coffeeComboBox.getSelectedIndex();
			// calls to retrieve appropriate coffee unit price to display under coffee combo box
			CoffeeCostCalculation myCalc = new CoffeeCostCalculation(indexInteger);
				unitPriceLabel.setText("Unit Price: " + valueDecimalFormat.format(myCalc.getCoffeePrice()));
		}
	}
	
	/*actionPerformed method
	 * When action listener to the quantityTextField, addButton, clearButton, and completeButton fire an event
	 * ***quantityTextField/addButton - if validation is successful then it calls addOrder method
	 * *** completeButton - calls the completeOrder method
	 * ***clearButton - calls the clearOrder method
	 */
	public void actionPerformed(ActionEvent evt)
	{
		// get the object that triggered the event
		Object sourceObject = evt.getSource();
		
		if(sourceObject == quantityTextField || sourceObject == addButton)
		{
		// if validation is successful, add the order
		if(validation())
			addOrder();
		}
		if(sourceObject == completeButton)
		{
			// calls to display order to JOptionPane
			completeOrder();
		}
		if(sourceObject == clearButton)
		{
			// calls to clear the current order selections
			clearOrder();
		}
	}
	
	/* selectCoffeeType method
	 * - if a coffee type has been selected from the coffee combo box then the selectedBoolean is true
	 * 		else if a coffee type hasn't been selected then selectedBoolean is false
	 */		
	public boolean selectCoffeeType()
	{
		boolean selectedBoolean;
		if(!(coffeeComboBox.getSelectedIndex() == 0))
		{
			selectedBoolean = true;
		}
		else
		{
			selectedBoolean = false;
		}
		return selectedBoolean;
	}
	
	/* validation method
	 * ***IF name has been filled out then check if quantityTextField is empty
	 * 			ELSE - prompt user to enter name and validationBoolean is false. Focus on nameTextField.
	 * ***IF quantity has been filled out check if coffee type has been selected
	 * 			ELSE - prompt user to enter quantity and validationBoolean is false. Focus on quantityTextField.
	 * ***IF coffee type has been selected then the validationBoolean is true
	 * 			ELSE - prompt user to select a coffee type and validationBoolean is false. Focus on coffee combo box.
	 */
	public boolean validation()
	{
		boolean validationBoolean;
		
		if(!(nameTextField.getText()).equals(""))
		{
			if(!(quantityTextField.getText()).equals(""))
			{
				if(selectCoffeeType())
				{
					validationBoolean = true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select a flavor");
					coffeeComboBox.requestFocus();
					validationBoolean = false;	
				}
			}
			else
			{
				JOptionPane.showMessageDialog(null, "Please enter the quantity");
				quantityTextField.requestFocus();
				validationBoolean = false;
			}	
		}
		else
		{
			JOptionPane.showMessageDialog(null, "Please enter name");
			nameTextField.requestFocus();
			validationBoolean = false;
		}
		return validationBoolean;
	}
	
	/* addOrder method
	 * - Retrieve which coffee type has been selected from the coffee combo box
	 * - Retrieve if decaf check box is selected or not
	 * - Try to convert quantity into integer. If failed, prompt user to enter a quantity number and focus on quantityTextField
	 * 		if successful then proceed to quantity validation.
	 * - Check if the user has exceeded the 10 per coffee type limit. If so, prompt that they cannot order more than 10 drinks
	 * 	 if not, then calculate the order, display in text area, reset the order selection fields, and enable the complete order button
	 */
	public void addOrder()
	{
		// Local variables
		int quantityInteger;
		float itemCostFloat = 0.0f;
		boolean decafSelectBoolean;
		String decafSelectString, flavorSelectString = "";
		
		// Retrieve the flavor selected
		if(coffeeComboBox.getSelectedIndex() == 1)
		{
			flavorSelectString = "Mocha";
		}
		else if(coffeeComboBox.getSelectedIndex() == 2)
		{
			flavorSelectString = "Latte";
		}
		else if(coffeeComboBox.getSelectedIndex() == 3)
		{
			flavorSelectString = "Drip";
		}
		
		// Retrieve if decaf is selected or not
		if (decafCheckBox.isSelected())
		{
			decafSelectBoolean = true;
		}
		else
		{
			decafSelectBoolean = false;
		}
		
		try
		{
			// In the try block, to check if the user enters a number for quantity
			//convert to int datatype
			quantityInteger = Integer.parseInt(quantityTextField.getText());
			
			CoffeeCostCalculation myCalc = new CoffeeCostCalculation(decafSelectBoolean, quantityInteger, flavorSelectString);
				
			// coffee quantity validation - checks if more than 10 of a coffee type is ordered
			
			if(myCalc.getValidation())
			{
			// Calculate the totals for the customer
		
			
			itemCostFloat = myCalc.getItemCost();
			decafSelectString = myCalc.getDecaf();
			// Call the displayAdd method to display output
			displayAdd(itemCostFloat, decafSelectString, flavorSelectString);
			// Call the clearOrder method to clear textfields
			// Enable the completeButton
			clearOrder();
			completeButton.setEnabled(true);
			}
			else
			{
				// If the user enters more than 10 as quantity
				// JOptionPane will alert the user
				// quantityTextField will be selected and have the focus
				JOptionPane.showMessageDialog(null, "Sorry, you may not purchase more than 10 drinks");
				quantityTextField.selectAll();
				quantityTextField.requestFocus();
			}
			
		}
		catch(NumberFormatException err)
		{
			// - If the user doesn't enter a number for quantity
			//   JOptionPane will alert the user
			// - quantityTextField will be selected and have the focus
			JOptionPane.showMessageDialog(null, "Please enter a quantity number");
			quantityTextField.selectAll();
			quantityTextField.requestFocus();
		}
	}
	
	/* displayAdd method
	 * - create the DecimalFormat object to format currency as "$0.00"
	 * - convert quantity into integer 
	 * - output the name, quantity, coffee flavor, decaf selection, and formatted item cost to the text area
	 */
	public void displayAdd(float newItemCostFloat, String newDecafSelectString, String newFlavorSelectString)
	{
		// instance variables
		int quantityInteger;
		String nameString;
		
		// Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		
		// Convert quantity into integer and store into quantityInteger
		quantityInteger = Integer.parseInt(quantityTextField.getText());
		nameString = nameTextField.getText();

		 // Format itemCostFloat with $ and 2 decimal places
		String formattedCurrency = valueDecimalFormat.format(newItemCostFloat);
		
		// Format the customer information for name, quantity, coffee type and item total and display in text area
		String outputString = String.format("%-8s %-8d %-8s %-7s %-7s %n", nameString, quantityInteger, newFlavorSelectString, newDecafSelectString, formattedCurrency);
		outputTextArea.append(outputString);
	}
	/*
	 * clearAll method
	 * - Reset the subtotal, coffee combo box, output text area, and decaf check box
	 * - Clear the name and quantity text fields
	 * - Set the title bar in the text area
	 * - Place cursor back into name text field
	 * - Disable the complete order button
	 */
	public void clearAll()
	{
		// reset subtotal
		float subTotalFloat = 0;
		CoffeeCostCalculation myReset = new CoffeeCostCalculation(subTotalFloat);
		// Clear the text fields
		nameTextField.setText("");
		quantityTextField.setText("");
		// Set the coffee combo box to [[ Select a Coffee ]]
		coffeeComboBox.setSelectedIndex(0);
		// Clear text area and set the title in text area
		outputTextArea.setText("");
		String titleString = String.format("%-8s %-8s %-8s %-8s %-8s %n", "Name:", "Qty", "Type", "Decaf", "Total");
		outputTextArea.append(titleString);
		
		// Place the cursor back in the nameTextField
		nameTextField.requestFocus();
		decafCheckBox.setSelected(false);
		completeButton.setEnabled(false);
	}
	/* clearOrder method
	 * - reset quantity text field, and coffee combo box
	 * - deselect decaf check box
	 * - place cursor back in quantity text field
	 */
	public void clearOrder()
	{
	    //clear the quantity text field
		quantityTextField.setText("");
		//set coffee combo box to [[ Select a Coffee ]]
		coffeeComboBox.setSelectedIndex(0);
		//place the cursor back in quantityTextField
		quantityTextField.requestFocus();
		//deselect the decaf check box
		decafCheckBox.setSelected(false);
	}
	
	/*
	 * completeOrder method
	 * - Do the final calculations for tax and grand total
	 * - Format the results with "$0.00"
	 * - Display the subtotal, tax, and grand total through JOptionPane
	 * - Call the clearAll method to reset everything for the next customer
	 */
	public void completeOrder()
	{	
		CoffeeCostCalculation myCalc = new CoffeeCostCalculation();
		// Format the values to currency format
		DecimalFormat valueDecimalFormat = new DecimalFormat("$#0.00");
		
		// Display the accumulated totals in JOptionPane
		JOptionPane.showMessageDialog(null, "Subtotal: " + valueDecimalFormat.format(myCalc.getSubTotal()) + '\n' + 
											"Tax: " + valueDecimalFormat.format(myCalc.getTax()) + '\n' + 
											"Grand Total: " + valueDecimalFormat.format(myCalc.getGrandTotal()));
		clearAll();
	}
}
