package cn.gyw.corejava.enumrate;
/**
 * 使用接口组织枚举
 * @author guanyw
 *
 */
public interface Food {

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
