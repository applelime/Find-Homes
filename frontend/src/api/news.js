import { apiInstance } from "./index.js";

const api = apiInstance();

async function getNewList(success, fail) {
  await api
    .get(`/news`)
    .then(success)
    .catch(fail);
}
export { getNewList };
