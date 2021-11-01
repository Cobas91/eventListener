const createHeader = JWT => {
  return {
    headers: {
      Authorization: `Bearer ${JWT}`,
    },
  }
}

export { createHeader }
