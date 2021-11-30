/*
 * Copyright (c) 2021. Zapliance GmbH All Rights Reserved.
 * You may use, distribute and modify this code under the terms of the Zapliance license,
 * which unfortunately won't be written for another century.
 * You should have received a copy of the Zapliance license with
 * this file. If not, please visit : https://zapliance.com
 */

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
