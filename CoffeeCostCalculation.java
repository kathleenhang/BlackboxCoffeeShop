/* Project 		: Project6KHang
 * Class 		: CoffeeCostCalculation
 * Date			: 04/28/2017
 * Programmer	: Kathleen Hang
 * Description	: This class is used to calculate coffee orders and validate that the customer can only order a max 
 * 				  of 10 drinks per coffee type.
 */

public class CoffeeCostCalculation
{
	private final float MOCHA_FLOAT = 3.75f, LATTE_FLOAT = 3.25f, DRIP_FLOAT = 1.75f;
	private int quantityInteger;
	private float taxFloat, itemCostFloat, coffeePriceFloat, grandTotalFloat;
	private String coffeeTypeString, decafSelectString;
	private boolean validationBoolean = false;
	
	private static float subTotalFloat;
	private static int mochaQuantityInteger, latteQuantityInteger, dripQuantityInteger;
	
	/*
	 * CoffeeCostCalculation - Constructor
	 * - Calls the finalCalculation method to calculate tax and grand total
	 */
	public CoffeeCostCalculation()
	{
		finalCalculation();
	}
	
	/*
	 * CoffeeCostCalculation - Constructor (accepts int in parameter)
	 * - calls setCoffeePrice method to decide which coffee unit price to display in the main class
	 * - calls setCoffeeLimit method to validate that the customer orders 10 or less coffee of a type
	 */
	public CoffeeCostCalculation(int coffeeIndexNewInteger)
	{
		setCoffeePrice(coffeeIndexNewInteger);
		
	}
	
	/*
	 * CoffeeCostCalculation - Constructor (accepts float in parameter)
	 * - calls setSubTotalReset method to reset the subtotal to 0
	 */
	public CoffeeCostCalculation(float subTotalNewFloat)
	{
		setSubTotalReset(subTotalNewFloat);
	}

	/*
	 * CofffeeCostCalculation - Constructor (accepts boolean, int, String in parameter)
	 * - Calls setDecaf method to set decaf selection as "Yes" or "No"
	 * - Calls setQuantity method to set the public quantity to private
	 * - Calls setCoffeeType method to set public coffee type to private
	 * - Calls calculate method to determine the item cost according to appropriate coffee type
	 * 		and accumulates the item cost into subTotalFloat
	 */
	public CoffeeCostCalculation(boolean decafNewBoolean, int quantityNewInteger, String coffeeTypeNewString)
	{
		setDecaf(decafNewBoolean);
		setQuantity(quantityNewInteger);
		setCoffeeType(coffeeTypeNewString);
		calculate();
		
	}
	
	/*
	 * calculate method
	 * - Calculates the item cost of the currently selected coffee type
	 * - Accumulates the item cost into the subTotalFloat
	 */
	private void calculate()
	{
		System.out.print(mochaQuantityInteger);
		if(quantityInteger <= 10)
		{
			validationBoolean = true;
			
			if(coffeeTypeString == "Mocha" && mochaQuantityInteger <= 10)
			{
				itemCostFloat = quantityInteger * MOCHA_FLOAT;
			
				mochaQuantityInteger += quantityInteger;
				
			}
			else if(coffeeTypeString == "Latte" && latteQuantityInteger <= 10)
			{
				itemCostFloat = quantityInteger * LATTE_FLOAT;
			
				latteQuantityInteger += quantityInteger;
			}
			else if(coffeeTypeString == "Drip" && dripQuantityInteger <= 10)
			{	
				itemCostFloat = quantityInteger * DRIP_FLOAT;
			
				dripQuantityInteger += quantityInteger;
			}
		}
		
		
		subTotalFloat += itemCostFloat;
	}
	
	/* finalCalculation method
	 * - Calculates the total tax and grand total of the coffee order
	 */
	private void finalCalculation()
	{
		final float SALES_TAX_FLOAT = .0975f;
		taxFloat = subTotalFloat * SALES_TAX_FLOAT;
		grandTotalFloat = subTotalFloat + taxFloat;
	}

	/* setCoffeeLimit method
	 * - validates that coffee quantity is no more than 10 per coffee type
	 * - sets the validationBoolean to true if rules are obeyed
	 */

	/* setCoffeePrice method
	 * - Sets the private coffeePriceFloat to the appropriate coffee flavor unit price according to 
	 * 	 which flavor the user has selected in the coffee combo box of main class 
	 */
	private void setCoffeePrice(int coffeeIndexNewInteger)
	{
		if(coffeeIndexNewInteger == 1)
		{
			coffeePriceFloat = MOCHA_FLOAT;
		}
		else if(coffeeIndexNewInteger == 2)
		{
			coffeePriceFloat = LATTE_FLOAT;
		}
		else if(coffeeIndexNewInteger == 3)
		{
			coffeePriceFloat = DRIP_FLOAT;
		}
		else
		{
			coffeePriceFloat = 0.0f;
		}
	}
	
	/* setCoffeeType method
	 * - set public coffee flavor type to private
	 */
	private void setCoffeeType(String coffeeSelectNewString)
	{
		coffeeTypeString = coffeeSelectNewString;
	}

	/* setDecaf method
	 * - use public decaf selection to determine if private decafSelectString will be "Yes" or "No"
	 */
	private void setDecaf(boolean decafSelectNewBoolean)
	{
		if(decafSelectNewBoolean)
		{
			decafSelectString = "Yes";
		}
		else
		{
			decafSelectString = "No";
		}
	}
	
	/* setSubTotalReset method
	 * - reset the subtotal
	 */
	private void setSubTotalReset(float subTotalNewFloat)
	{
		subTotalFloat = 0;
	}
	
	/* setQuantity
	 * - set the public quantity to private
	 */
	private void setQuantity(int quantityNewInteger)
	{
		quantityInteger = quantityNewInteger;
	}
	
	/* getCoffeePrice method 
	 * - Retrieve the private coffee type unit price
	 */
	public float getCoffeePrice()
	{
		return coffeePriceFloat;
	}
	
	/* getCoffeeType method
	 * - Retrieve the private coffee flavor type 
	 */
	public String getCoffeeType()
	{  
		return coffeeTypeString;
	}

	/* getDecaf method
	 * - Retrieve the private decaf selection of "Yes" or "No"
	 */
	public String getDecaf()
	{
		return decafSelectString;
	}
	
	/* getGrandTotal method
	 * - Retrieve the private grand total of coffee order
	 */
	public float getGrandTotal()
	{
		return grandTotalFloat;
	}
	
	/* getItemCost method
	 * - Retrieve the private item cost 
	 */
	public float getItemCost()
	{
		
		return itemCostFloat;
	}
	
	/* getSubTotal method
	 * - Retrieve the private subtotal
	 */
	public float getSubTotal()
	{
		return subTotalFloat;
	}
	
	/* getTax method 
	 * - Retrieve the private tax total
	 */
	public float getTax()
	{
		return taxFloat;
	}
	
	/*  getValidation method
	 * - Retrieve the private validation boolean which determines if customer purchased no more than 10 of a coffee type
	 */
	public boolean getValidation()
	{
		return validationBoolean;
	}
}
