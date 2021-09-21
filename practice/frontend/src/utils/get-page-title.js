import defaultSettings from '@/settings'

const title = defaultSettings.title || 'practice'

export default function getPageTitle(pageTitle) {
  if (pageTitle) {
    return `${pageTitle} - ${title}`
  }
  return `${title}`
}
