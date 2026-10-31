function searchByNameAndMinPrice(name, minPrice) {
  // avg 2 sec to get products
  const products = getProductsByName(name);
  const matchingProductIDs = products
    .filter((p) => p.price >= minPrice)
    .map((p) => p.id);
  // avg 3 sec to get details
  const details = getProductDetails(matchingProductIDs);

  return details;
}
