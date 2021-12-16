export const debounce = (fn, wait = 1000) => {
    if (window.timeout) clearTimeout(window.timeout)
    window.timeout = setTimeout(() => {
        fn()
    }, wait)
}

