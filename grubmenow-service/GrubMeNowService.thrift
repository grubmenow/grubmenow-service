namespace java com.grubmenow.service
typedef i32 Integer
typedef string String

struct SearchQuery {
  1: String zipcode,
  2: Integer radius = 5,
  3: String availableDay
}

struct FoodItem {
  1: String foodItemId,
  2: String foodItemName,
  3: String foodItemImageUrl,
  4: String foodItemDescription
}

service GrubMeNowService {
	list<FoodItem> SearchFoodItems(1: SearchQuery searchQuery)
	list<FoodItem> SearchFoodItemsTest()
}

