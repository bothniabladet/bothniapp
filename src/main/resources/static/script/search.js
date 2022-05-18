const resultsElement = document.querySelector('#results');

const parser = new DOMParser();

const createResultElement = (item) => {
    return parser.parseFromString(
        `
        <li>${item.title}</li>
    `,
        'text/html'
    ).querySelector('li');
};

const showSearchResults = (result) => {
    let children = [...resultsElement.children];
    children.forEach(element => {
        resultsElement.removeChild(element)
    });

    result.map(createResultElement).forEach(element => {
        console.log(element)
        resultsElement.appendChild(element)
    });
};

const search = (query) => {
    return fetch(`/search?query=${query}`, {method: 'post'})
        .then(res => res.json());
};

document.querySelector('#search').addEventListener('keyup', (event) => {
    const query = event.target.value;

    if (query.length < 3)
        return;

    search(query)
        .then(showSearchResults)
        .catch(console.error);
});