import { useEffect, useState } from 'react'

export default function useResponsive() {
  const [windowDimension, setWindowDimension] = useState(null)
  useEffect(() => {
    setWindowDimension(window.innerWidth)
  }, [])

  useEffect(() => {
    function handleResize() {
      setWindowDimension(window.innerWidth)
    }

    window.addEventListener('resize', handleResize)
    return () => window.removeEventListener('resize', handleResize)
  }, [])
  const isMobile = windowDimension <= 640
  return { isMobile }
}
