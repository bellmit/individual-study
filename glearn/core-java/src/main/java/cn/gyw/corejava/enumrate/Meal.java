package cn.gyw.corejava.enumrate;

/**
 * 枚举的枚举
 * 组织枚举类型
 * @author guanyw
 *
 */
public enum Meal {
	Appetizer(Food.Appetizer.class),
	MainCourse(Food.MainCourse.class),
	Dessert(Food.Dessert.class),
	Coffee(Food.Coffee.class);

	private Food[] values;
	private Meal(Class<? extends Food> kind){
		values = kind.getEnumConstants();
	}

	interface Food {
		enum Appetizer implements Food{
			SALAD,SOUP,SPRING_ROLLS;
		}
		enum MainCourse implements Food{
			LASEGNE,BURRITO,PAD_THAI,LENTIL5,HUMMOUS,VINDALOO;
		}
		enum Dessert implements Food{
			TIRAMISU,GELATO,BLACK_FOREST_CAKE,FRUIT,CREME_CARAMEL;
		}
		enum Coffee implements Food{
			BLACK_COFFEE,DECAF_COFFEE,ESPRESSO,LATTE,CAPPUCCINO,TEA,HERB_TEA;
		}
	}

	public Food randomSelection(){
		// random a Food
		return values[0];
	}

	public static void main(String[] args) {
		for(int i=0;i<5;i++){
			for(Meal meal : Meal.values()){
				Food food = meal.randomSelection();
				System.out.println(food);
			}
			System.out.println("------------");
		}
	}
}
