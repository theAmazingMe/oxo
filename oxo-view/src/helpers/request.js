const requestTheApi = async (uri,method,body,next) => {
    fetch(uri, {
      method,
      headers: { 'Content-Type': 'application/json', },
      body: JSON.stringify(body)
    }).then(data => {
      return data.json()
    }).then(next)
  }
  export default requestTheApi