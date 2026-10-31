getUserInfo()
  .then(user => getPendingDebt(user.id))
  .then(debt => console.log(
    debt.amount === 0 ?
      `No pending debt for user ${user.name}. Yay!` :
      `You have a pending debt of $${debt.amount}. Please PAY NOW !!!`
  ))
  .catch(err => console.error('Error fetching user or debt:', err));
