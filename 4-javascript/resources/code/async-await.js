const task = async () => {
  try {
    const user = await getUserInfo();
    const debt = await getPendingDebt(user.id);

    if (debt.amount === 0) {
      console.log(`No pending debt for user ${user.name}. Congrats!`);
    } else {
      console.log(`You have a pending debt of $${debt.amount}. :((`);
    }
  } catch (err) {
    console.error('Error fetching user or debt. Reason:', err);
  }
};

task();
